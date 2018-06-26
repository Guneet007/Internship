from django.conf.urls import url, include
from . import views
from rest_framework.urlpatterns import format_suffix_patterns
from rest_framework import routers
from .views import Users

urlpatterns = [
    # PUT, GET & DELETE. To get, update and delete orders
    url(r'^user/(?P<user_id>[0-9]+)/order/(?P<order_id>[0-9]+)$', views.UserOrders.as_view()),

    # Get orders for a user based on user id POST, create new order
    url(r'^user/(?P<user_id>[0-9]+)/order$', views.UserOrders.as_view()),

    # Search order based on user id with filter
    url(r'^user/(?P<user_id>[0-9]+)/orders', views.OrderFilter.as_view()),

    url(r'^nutrition/$', views.Nutritional.as_view()),  # Get all nutrition (GET / POST)
    url(r'^products/$', views.ProductApi.as_view()),  # Get all products (GET / POST)
    url(r'^ingredients/$', views.IngredientsApi.as_view()),  # Get all ingredients (GET / POST)
    url(r'^orders/$', views.OrderFilter.as_view()),  # Get all orders with filter (GET)
    url(r'^users/$', views.Users.as_view()),  # Get all users and post (GET / POST)
    url(r'^metrics$', views.MetricsAndUpload.as_view()),  # Get metrics (GET)
    url(r'^upload/(?P<filename>[^/]+)?$', views.FileUploadView.as_view()),  # Upload a file (POST)
    url(r'^generateData', views.MetricsAndUpload.as_view()),  # Upload a file (POST)

]

urlpatterns = format_suffix_patterns(urlpatterns)
