<?php

namespace AppBundle\Controller;

use AppBundle\Entity\Orders;
use FOS\RestBundle\View\View;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use FOS\RestBundle\Controller\FOSRestController;
use FOS\RestBundle\Controller\Annotations as Rest;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;

class OrderController extends FOSRestController {

    /**
     * Get the order with order id
     * 
     * @Rest\Get("/v2/user/{userId}/order/{orderId}")
     */
    public function idAction($userId, $orderId) {
        try {
            $order = $this->getDoctrine()->getRepository('AppBundle:Orders')
                    ->findOneBy(['id' => $orderId, 'userId' => $userId]);
            if (!$order) {
                return new View(['status' => 'success', 'data' => ['message' => 'Order not found.']], Response::HTTP_NOT_FOUND);
            }
            return new View(['status' => 'success', 'data' => ['order' => $order]], Response::HTTP_OK);
        } catch (\Exception $ex) {
            return new View(['status' => 'error', 'error' => ['message' => $ex->getMessage()]], Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a new order
     * 
     * @Rest\Post("/v2/user/{userId}/order")
     */
    public function postAction($userId, Request $request) {
        try {
            $order = new Orders;
            $date = new \DateTime('now', new \DateTimeZone('UTC'));
            $order->setUserId($userId);
            $order->setDate($date);
            $em = $this->getDoctrine()->getManager();
            foreach ($request->get('products') as $product) {
                $product = $em->getRepository('AppBundle:Products')->find($product['id']);
                $order->addProduct($product);
            }
            $em->persist($order);
            $em->flush();
            return new View(['status' => 'success', 'data' => ['order' => $order]], Response::HTTP_CREATED);
        } catch (\Exception $ex) {
            return new View(['status' => 'error', 'error' => ['message' => $ex]], Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates an existing order
     * 
     * @Rest\Put("/v2/user/{userId}/order/{orderId}")
     */
    public function putAction($userId, $orderId, Request $request) {
        try {
            $order = $this->getDoctrine()->getRepository('AppBundle:Orders')
                    ->findOneBy(['id' => $orderId, 'userId' => $userId]);
            if (!$order) {
                return new View(['status' => 'error', 'error' => ['message' => "Order not found."]], Response::HTTP_NOT_FOUND);
            }
            $date = new \DateTime('now', new \DateTimeZone('UTC'));
            $order->setUserId($userId);
            $order->setDate($date);
            foreach ($order->getProducts() as $product) {
                $order->removeProduct($product);
            }
            $em = $this->getDoctrine()->getManager();
            foreach ($request->get('products') as $product) {
                $product = $em->getRepository('AppBundle:Products')->find($product['id']);
                $order->addProduct($product);
            }
            $em->persist($order);
            $em->flush();
            return new View(['status' => 'success', 'data' => ['order' => $order]], Response::HTTP_CREATED);
        } catch (\Exception $ex) {
            return new View(['status' => 'error', 'error' => ['message' => $ex]], Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Delete an existing order
     * 
     * @Rest\Delete("/v2/user/{userId}/order/{orderId}")
     */
    public function deleteAction($userId, $orderId) {
        try {
            $order = $this->getDoctrine()->getRepository('AppBundle:Orders')
                    ->findOneBy(['id' => $orderId, 'userId' => $userId]);
            if (!$order) {
                return new View(['status' => 'error', 'error' => ['message' => "Order not found."]], Response::HTTP_NOT_FOUND);
            }
            foreach ($order->getProducts() as $product) {
                $order->removeProduct($product);
            }
            $em = $this->getDoctrine()->getManager();
            $em->remove($order);
            $em->flush();
            return new View(['status' => 'success', 'data' => ['order' => $order]], Response::HTTP_CREATED);
        } catch (\Exception $ex) {
            return new View(['status' => 'error', 'error' => ['message' => $ex]], Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Search an order based on Id
     * 
     * @Rest\Get("/v2/user/{userId}/orders")
     */
    public function getAction($userId) {
        try {
            $orders = $this->getDoctrine()->getRepository('AppBundle:Orders')
                    ->findBy(['userId' => $userId]);
            return new View(['status' => 'success', 'data' => ['orders' => $orders]], Response::HTTP_OK);
        } catch (\Exception $ex) {
            return new View(['status' => 'error', 'error' => ['message' => $ex->getMessage()]], Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Search an order based on price and date
     * 
     * @Rest\Get("/v2/orders")
     */
    public function searchAction(Request $request) {
        try {
            $price = $request->get('price');
            $startDate = $request->get('startDate');
            $endDate = $request->get('endDate');
            $em = $this->getDoctrine()->getManager();
            $orders = $em->getRepository('AppBundle:Orders');
            $query = $orders->createQueryBuilder('o')
                    ->select('o')
                    ->innerJoin('o.products', 'p')
                    ->addSelect('p')
                    ->addSelect('SUM(p.price) AS total_price');
            if ($price) {
                $query->having('total_price = :price')->setParameter('price', $price);
            }
            if ($startDate && $endDate) {
                $query->where('o.date BETWEEN :startDate AND :endDate')
                        ->setParameter('startDate', new \DateTime($startDate, new \DateTimeZone('UTC')))
                        ->setParamter('endDate', new \DateTime($endDate, new \DateTimeZone('UTC')));
            }
            $paginator = new \Doctrine\ORM\Tools\Pagination\Paginator($query);
            $currentPage = ($request->get('page')) ? $request->get('page') : 1;
            $perPage = 1;
            $totalItems = count($paginator);
            $results = $query->getQuery()
                    ->setMaxResults($perPage)
                    ->setFirstResult($perPage * ($currentPage - 1))
                    ->getResult();
            $response = [
                'orders' => $results,
                'pageNum' => (int) $currentPage,
                'perPage' => $perPage,
                'pageSize' => ceil($totalItems / $perPage),
                'totalItems' => $totalItems
            ];
            return new View(['status' => 'success', 'data' => $response], Response::HTTP_OK);
        } catch (\Exception $ex) {
            return new View(['status' => 'error', 'error' => ['message' => $ex->getMessage()]], Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }

}
