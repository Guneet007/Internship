<?php

namespace AppBundle\Controller;

use FOS\RestBundle\View\View;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use FOS\RestBundle\Controller\FOSRestController;
use FOS\RestBundle\Controller\Annotations as Rest;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;

class FileController extends FOSRestController {

    /**
     * @Rest\Post("/v2/upload")
     */
    public function uploadAction(Request $request) {
        try {
            $file = $request->files->get('file');
            $file->move('files', $file);
            return new View(['status' => 'success', 'data' => ['message' => 'File uploaded']], Response::HTTP_OK);
        } catch (\Exception $ex) {
            return new View(['status' => 'error', 'error' => ['message' => $ex->getMessage()]], Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }

}
