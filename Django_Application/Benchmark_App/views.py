# -*- coding: utf-8 -*-
from __future__ import unicode_literals

import os
import psutil
import multiprocessing

from rest_framework.generics import ListAPIView
from rest_framework.parsers import FileUploadParser, MultiPartParser
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from django.http import JsonResponse, Http404

from .models import NutritionalInfo, Ingredients, Product, Order, User
from .serializers import NutritionalSerializer, IngredientsSerializer, ProductSerializer, OrderSerializer, \
    UserSerializers
from datetime import datetime
from .pagination import PostLimitOffsetPagination, PostPageNumberPagination
from psutil import virtual_memory

DATA_DUMP_FOLDER = "data_dump"

SYMBOLS = {
    'customary': ('B', 'K', 'M', 'G', 'T', 'P', 'E', 'Z', 'Y'),
    'customary_ext': ('byte', 'kilo', 'mega', 'giga', 'tera', 'peta', 'exa',
                      'zetta', 'iotta'),
    'iec': ('Bi', 'Ki', 'Mi', 'Gi', 'Ti', 'Pi', 'Ei', 'Zi', 'Yi'),
    'iec_ext': ('byte', 'kibi', 'mebi', 'gibi', 'tebi', 'pebi', 'exbi',
                'zebi', 'yobi'),
}


class Nutritional(APIView):

    def get(self, request):
        """This function returns list of nutrition

        :param request: request header
        :return: json response
        """
        nutritional_info = NutritionalInfo.objects.all()
        serializer = NutritionalSerializer(nutritional_info, many=True)
        return JsonResponse({"NutritionalInfo": serializer.data})

    def post(self, request):
        """This function creates new Nutrition

        :param request: request header
        :return: json response
        """
        serializer = NutritionalSerializer(data=request.data)
        print request.data
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response(serializer.data, status=status.HTTP_400_BAD_REQUEST)


class IngredientsApi(APIView):

    def get(self, request):
        """This function returns list of ingredients

        :param request: request header
        :return: json response
        """
        ingredients = Ingredients.objects.all()
        serializer = IngredientsSerializer(ingredients, many=True)
        return JsonResponse({"Ingredients": serializer.data})

    def post(self, request):
        """This function creates new Ingredient

        :param request: request header
        :return: json response
        """
        serializer = IngredientsSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response(serializer.data, status=status.HTTP_400_BAD_REQUEST)


class ProductApi(APIView):

    def get(self, request):
        """This function returns list of products

        :param request: request header
        :return: json response
        """
        products = Product.objects.all()
        serializer = ProductSerializer(products, many=True)
        return JsonResponse({"products": serializer.data})

    def post(self, request):
        """This function creates new product

        :param request: request header
        :return: json response
        """
        serializer = ProductSerializer(data=request.data)
        print request.data
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response(serializer.data, status=status.HTTP_400_BAD_REQUEST)


class Users(APIView):

    def get(self, request):
        """This function returns list of users

        :param request: request header
        :return: json response
        """
        users = User.objects.all()
        serializer = UserSerializers(users, many=True)
        return JsonResponse({"Users": serializer.data})


class UserOrders(APIView):
    def get(self, request, user_id, order_id=None):
        """This function helps to get all the orders for a user based on user id

        :param request: request header
        :param user_id: String, user id
        :param order_id: String, order id
        :return: returns json response
        """
        if order_id:
            orders = Order.objects.filter(user_id=user_id, id=order_id)
        else:
            orders = Order.objects.filter(user_id=user_id)
        serializer = OrderSerializer(orders, many=True)
        return JsonResponse({"orders": serializer.data})

    def post(self, request, user_id):
        """This function helps to create a new order based on user id

        :param request: request header
        :param user_id: String, user_id
        :return: json response
        """
        products = [li['name'] for li in request.data['products']]
        products_list = [str(r) for r in products]
        if User.objects.filter(id=user_id).exists():
            my_order = Order.objects.create(user_id=user_id)
            product_status = self.add_products_for_order(products_list, my_order, 0)
            if product_status:
                return self.get_user_orders(user_id, my_order.pk)
            else:
                return Response({"message": "Product Not Found"}, status=status.HTTP_404_NOT_FOUND)
        else:
            return Response({"message": "User Not Found"}, status=status.HTTP_404_NOT_FOUND)

    def delete(self, request, user_id, order_id):
        """This function helps to delete an order based on order id and user id

        :param request: request header
        :param user_id: String, user id
        :param order_id: String, order id
        :return: returns json response
        """
        order = Order.objects.filter(user_id=user_id, id=order_id)
        if order.exists():
            order.delete()
            return Response({"message": "Order Deleted"}, status=status.HTTP_204_NO_CONTENT)
        else:
            return Response({"message": "UserId/Orderid not found"}, status=status.HTTP_404_NOT_FOUND)

    def put(self, request, user_id, order_id):
        """This function helps update the products in an order

        :param request: request header
        :param user_id: String, user id
        :param order_id: String, order id
        :return: returns json response
        """
        products = [li['name'] for li in request.data['products']]
        products_list = [str(r) for r in products]
        if User.objects.filter(id=user_id).exists():
            if Order.objects.filter(id=order_id).exists():
                my_order = Order.objects.get(user_id=user_id, id=order_id)
                my_order.product.clear()
                product_status = self.add_products_for_order(products_list, my_order, 0)
                if product_status:
                    return self.get_user_orders(user_id, order_id)
                else:
                    return Response({"message": "Product Not Found"}, status=status.HTTP_404_NOT_FOUND)
            else:
                return Response({"message": "Order Not Found"}, status=status.HTTP_404_NOT_FOUND)
        else:
            return Response({"message": "User Not Found"}, status=status.HTTP_404_NOT_FOUND)

    def get_user_orders(self, user_id, order_id):
        """This function helps get the orders based on user id and order id.

        :param user_id: String, user id
        :param order_id: String, order id
        :return: json response of order.
        """
        orders = Order.objects.filter(user_id=user_id, id=order_id)
        serializer = OrderSerializer(orders, many=True)
        return JsonResponse({"orders": serializer.data}, status=201)

    def add_products_for_order(self, products_list, my_order, total_price=0):
        """This function helps to add the selected products by the user to an order

        :param products_list: List of products selected by user
        :param my_order: order object
        :param total_price: int total price of all products
        :return: return flag
        """
        for product in products_list:
            if Product.objects.filter(name=product).exists():
                product = Product.objects.filter(name=product).values('id', 'price')
                product_id = product[0].get('id')
                total_price += product[0].get('price')
                my_product = Product.objects.get(pk=product_id)
                my_order.product.add(my_product)
                my_order.price = total_price
                my_order.save()
            else:
                return False
        return True


class OrderFilter(ListAPIView):
    """
    This class helps to filter orders and search orders
    """
    serializer_class = OrderSerializer
    pagination_class = PostPageNumberPagination
    lookup_url_kwarg = "user_id"

    def get_queryset(self):
        """This functions helps get all the query set for oders

        :return: list of orders
        """
        kwargs = {}
        user_id = self.kwargs.get(self.lookup_url_kwarg)

        if user_id:
            if Order.objects.filter(user_id=user_id).exists():
                kwargs['user_id'] = user_id
                return self.get_orders(kwargs, self.request)
            else:
                raise Http404("User does not exist")
        else:
            return self.get_orders(kwargs, self.request)

    def get_orders(self, kwargs, request):
        """This is a helper function that helps to filter all orders

        :param kwargs: kwargs are passed in order to help filter orders
        :param request: request header
        :return: list of orders
        """
        page_size = request.query_params.get('pageSize', None)
        order_id = request.query_params.get('order_id', None)
        price = request.query_params.get('price', None)
        start_date = request.query_params.get('startDate', None)
        end_date = request.query_params.get('endDate', None)
        if page_size:
            self.pagination_class.page_size = page_size
        if order_id:
            kwargs['id'] = order_id
        if price:
            kwargs['price'] = price
        if start_date and end_date:
            start_date = datetime.strptime(start_date, "%d-%m-%Y").strftime("%Y-%m-%d")
            end_date = datetime.strptime(end_date, "%d-%m-%Y").strftime("%Y-%m-%d")
            kwargs['date__range'] = [start_date, end_date]

        orders = Order.objects.filter(**kwargs)
        return orders


class MetricsAndUpload(APIView):
    parser_classes = (MultiPartParser,)

    def get(self, request):
        """This function helps provide the system metrics

        :param request:request header
        :return: metrics
        """
        total_physical_memory = virtual_memory()

        process = psutil.Process(os.getpid())
        bytes_num = process.memory_info().rss

        memory_usage = total_physical_memory.total / bytes_num
        memory_usage = memory_usage / 100

        processor_usage = str(psutil.cpu_percent()) + " %"

        return JsonResponse({"metrics": {
            "cpuCores": str(multiprocessing.cpu_count()) + " %",
            "cpuUsage": processor_usage,
            "memory": self.format_bytes(total_physical_memory.total),
            "memoryUsage": str(memory_usage) + " %",
            "diskSpaceUsage": "45MB"
        }})

    def format_bytes(self, bytes_num):
        """This function helps format bytes to human readable format

        :param bytes_num:
        :return: string, memory
        """
        sizes = ["B", "KB", "MB", "GB", "TB"]

        i = 0
        dblbyte = bytes_num

        while i < len(sizes) and bytes_num >= 1024:
            dblbyte = bytes_num / 1024.0
            i = i + 1
            bytes_num = bytes_num / 1024

        return str(round(dblbyte, 2)) + " " + sizes[i]


class FileUploadView(APIView):
    """View for uploaded file."""

    parser_classes = (FileUploadParser,)

    def post(self, request, filename, format=None):
        """Put view for file upload.

        :param request: object, http request object
        :param filename: str, name of the file uploaded
        :param format: , format of the file uploaded, default is none
        :return: Response, object
        """
        file_obj = request.FILES['file']
        path = os.path.dirname(os.path.abspath(__file__))
        path = os.path.join(path, DATA_DUMP_FOLDER)

        if not os.path.exists(path):
            os.makedirs(path)

        destination = open(path + "/" + filename, 'wb+')
        for chunk in file_obj.chunks():
            destination.write(chunk)
            destination.close()
        return Response(status=204)
