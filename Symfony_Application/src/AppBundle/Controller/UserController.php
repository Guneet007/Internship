<?php

namespace AppBundle\Controller;

use AppBundle\Entity\Users;
use FOS\RestBundle\View\View;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use FOS\RestBundle\Controller\FOSRestController;
use FOS\RestBundle\Controller\Annotations as Rest;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;

class UserController extends FOSRestController {

    /**
     * Returns the details of user by Id
     * 
     * @Rest\Get("/v2/user/{id}")
     */
    public function idAction($id) {
        try {
            $user = $this->getDoctrine()->getRepository('AppBundle:Users')->find($id);
            if ($user) {
                return new View(['status' => 'success', 'data' => ['user' => $user]], Response::HTTP_OK);
            }
            return new View(['status' => 'success', 'data' => ['message' => 'User not found.']], Response::HTTP_NOT_FOUND);
        } catch (\Exception $ex) {
            return new View(['status' => 'error', 'error' => ['message' => $ex->getMessage()]], Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Returns the list of users
     * 
     * @Rest\Get("/v2/user")
     */
    public function getAction() {
        try {
            $users = $this->getDoctrine()->getRepository('AppBundle:Users')->findAll();
            return new View(['status' => 'success', 'data' => ['users' => $users]], Response::HTTP_OK);
        } catch (\Exception $ex) {
            return new View(['status' => 'error', 'error' => ['message' => $ex->getMessage()]], Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a new users
     * 
     * @Rest\Post("/v2/user")
     */
    public function postAction(Request $request) {
        try {
            $user = new Users;
            $name = $request->get('name');
            $email = $request->get('email');
            $phone = $request->get('phone');
            $address = $request->get('address');
            if (empty($name) || empty($email) || empty($phone) || empty($address)) {
                return new View(['status' => 'fail', 'data' => ['message' => "Null values are not allowed."]], Response::HTTP_NOT_ACCEPTABLE);
            }
            $user->setName($name);
            $user->setEmail($email);
            $user->setPhone($phone);
            $user->setAddress($address);
            $em = $this->getDoctrine()->getManager();
            $em->persist($user);
            $em->flush();
            return new View(['status' => 'success', 'data' => ['user' => $user]], Response::HTTP_CREATED);
        } catch (\Exception $ex) {
            return new View(['status' => 'error', 'error' => ['message' => $ex->getMessage()]], Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }

}
