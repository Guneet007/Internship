<?php

namespace AppBundle\Controller;

use AppBundle\Entity\Products;
use FOS\RestBundle\View\View;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use FOS\RestBundle\Controller\FOSRestController;
use FOS\RestBundle\Controller\Annotations as Rest;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;

class ProductController extends FOSRestController {

    /**
     * Returns the list of products
     * 
     * @Rest\Get("/v2/product")
     */
    public function getAction() {
        try {
            $products = $this->getDoctrine()->getRepository('AppBundle:Products')->findAll();
            return new View(['status' => 'success', 'data' => ['products' => $products]], Response::HTTP_OK);
        } catch (\Exception $ex) {
            return new View(['status' => 'error', 'error' => ['message' => $ex->getMessage()]], Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a new product
     * 
     * @Rest\Post("/v2/product")
     */
    public function postAction(Request $request) {
        try {
            $product = new Products;
            $name = $request->get('name');
            $price = $request->get('price');
            if (empty($name) || empty($price)) {
                return new View(['status' => 'fail', 'data' => ['message' => "Null values are not allowed."]], Response::HTTP_NOT_ACCEPTABLE);
            }
            $product->setName($name);
            $product->setPrice($price);
            $em = $this->getDoctrine()->getManager();
            foreach ($request->get('ingredients') as $ingredient) {
                $ingredient = $em->getRepository('AppBundle:Ingredients')->find($ingredient['id']);
                $product->addIngredient($ingredient);
            }
            $em->persist($product);
            $em->flush();
            return new View(['status' => 'success', 'data' => ['product' => $product]], Response::HTTP_CREATED);
        } catch (\Exception $ex) {
            return new View(['status' => 'error', 'error' => ['message' => $ex->getMessage()]], Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }

}
