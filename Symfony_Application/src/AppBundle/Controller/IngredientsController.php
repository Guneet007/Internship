<?php

namespace AppBundle\Controller;

use FOS\RestBundle\View\View;
use AppBundle\Entity\Ingredients;
use AppBundle\Entity\NutritionalInfo;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use FOS\RestBundle\Controller\FOSRestController;
use FOS\RestBundle\Controller\Annotations as Rest;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;

class IngredientsController extends FOSRestController {

    /**
     * Returns the list of ingredients
     * 
     * @Rest\Get("/v2/ingredients")
     */
    public function getAction() {
        try {
            $ingredients = $this->getDoctrine()->getRepository('AppBundle:Ingredients')->findAll();
            return new View(['status' => 'success', 'data' => ['ingredients' => $ingredients]], Response::HTTP_OK);
        } catch (\Exception $ex) {
            return new View(['status' => 'error', 'error' => ['message' => $ex->getMessage()]], Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a new ingredient
     * 
     * @Rest\Post("/v2/ingredient")
     */
    public function postAction(Request $request) {
        try {
            $ingredient = new Ingredients;
            $nutritionalInfo = new NutritionalInfo;
            $name = $request->get('name');
            $nutritionalInfos = $request->get('nutritionalInfo');
            if (empty($name) || empty($nutritionalInfo)) {
                return new View(['status' => 'fail', 'data' => ['message' => "Null values are not allowed."]], Response::HTTP_NOT_ACCEPTABLE);
            }
            $ingredient->setName($name);
            $em = $this->getDoctrine()->getManager();
            foreach ($nutritionalInfos as $nutrition) {
                $nutritionalInfo->setName($nutrition['name']);
                $nutritionalInfo->setValue($nutrition['value']);
                $nutritionalInfo->setUnit($nutrition['unit']);
                $nutritionalInfo->setIngredient($ingredient);
            }
            $em->persist($ingredient);
            $em->persist($nutritionalInfo);
            $em->flush();
            return new View(['status' => 'success', 'data' => ['ingredient' => $ingredient]], Response::HTTP_CREATED);
        } catch (\Exception $ex) {
            return new View(['status' => 'error', 'error' => ['message' => $ex->getMessage()]], Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }

}
