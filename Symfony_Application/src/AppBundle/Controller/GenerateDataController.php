<?php

namespace AppBundle\Controller;

use FOS\RestBundle\View\View;
use AppBundle\Entity\Users;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use FOS\RestBundle\Controller\FOSRestController;
use FOS\RestBundle\Controller\Annotations as Rest;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;

class GenerateDataController extends FOSRestController {

    /**
     * @Rest\Post("/v2/generateData")
     */
    public function generateDataAction() {
        try {
            $faker = \Faker\Factory::create();
            $em = $this->getDoctrine()->getManager();
            // Generate users
            for ($i = 0; $i < 10; $i++) {
                $user = new Users();
                $user->setName($faker->name);
                $user->setEmail($faker->unique()->email);
                $user->setPhone($faker->e164PhoneNumber);
                $user->setAddress($faker->address);
                $em->persist($user);
            }
            $em->flush();
            //Generate ingredients
            $ingredientNames = [
                'Tomato', 'Potato', 'Oil', 'Sugar', 'Butter', 'Milk', 'Meat', 'Honey', 'Egg', 'Rice'
            ];
            $nutritionalInfos = [
                'Carbohydrates', 'Starch', 'Protein', 'Fat', 'Vitamins', 'Minerals', 'Fiber', 'Iron', 'Riboflavin', 'Energy'
            ];
            for ($i = 0; $i < 10; $i++) {
                $ingredient = new \AppBundle\Entity\Ingredients();
                $nutritionalInfo = new \AppBundle\Entity\NutritionalInfo;
                $ingredient->setName($ingredientNames[$i]);
                for ($j = 0; $j < 3; $j++) {
                    $nutritionalInfo->setName($nutritionalInfos[$i]);
                    $nutritionalInfo->setValue($faker->randomFloat($nbMaxDecimals = 2, $min = 0, $max = 2));
                    $nutritionalInfo->setUnit('kCal');
                    $nutritionalInfo->setIngredient($ingredient);
                }
                $em->persist($ingredient);
                $em->persist($nutritionalInfo);
            }
            $em->flush();
            $productNames = [
                'Butter Milk', 'Yoghurt', 'Frooti', 'Amul Milk', 'Cake', 'Tea', 'Juice', 'Cheese', 'Cola', 'Pepsi'
            ];
            //Generate products
            for ($i = 0; $i < 10; $i++) {
                $product = new \AppBundle\Entity\Products();
                $ingredients = $this->getDoctrine()->getRepository('AppBundle:Ingredients')->findAll();
                $product->setName($productNames[$i]);
                $product->setPrice($faker->numberBetween($min = 10, $max = 1000));
                foreach ($ingredients as $ingredient) {
                    $product->addIngredient($ingredient);
                }
                $em->persist($product);
            }
            $em->flush();
            return new View(['status' => 'success', 'data' => ['message' => 'Data generated successfully.']], Response::HTTP_OK);
        } catch (\Exception $ex) {
            return new View(['status' => 'error', 'error' => ['message' => $ex->getMessage()]], Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }

}
