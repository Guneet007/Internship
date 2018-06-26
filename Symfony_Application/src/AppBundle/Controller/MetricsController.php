<?php

namespace AppBundle\Controller;

use FOS\RestBundle\View\View;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use FOS\RestBundle\Controller\FOSRestController;
use FOS\RestBundle\Controller\Annotations as Rest;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;

class MetricsController extends FOSRestController {

    /**
     * @Rest\Get("/v2/metrics")
     */
    public function getAction(Request $request) {
        try {
            $response = [
                'cpuCores' => $this->getCpuCores(),
                'cpuUsage' => $this->getCpuUsages(),
                'memory' => $this->getMemory(),
                'memoryUsage' => $this->getMemoryUsage(),
                'diskSpaceUsage' => $this->getDiskSpaceUsage()
            ];
            return new View(['status' => 'success', 'data' => ['metrics' => $response]], Response::HTTP_OK);
        } catch (\Exception $ex) {
            return new View(['status' => 'error', 'error' => ['message' => $ex->getMessage()]], Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }

    public function getCpuCores() {
        if (is_file('/proc/cpuinfo')) {
            $cpuinfo = file_get_contents('/proc/cpuinfo');
            preg_match_all('/^processor/m', $cpuinfo, $matches);
            return count($matches[0]);
        } else {
            return 1;
        }
    }

    public function getCpuUsages() {
        $load = sys_getloadavg();
        return $load[0];
    }

    public function getMemory() {
        $fh = fopen('/proc/meminfo', 'r');
        $mem = 0;
        while ($line = fgets($fh)) {
            $pieces = array();
            if (preg_match('/^MemTotal:\s+(\d+)\skB$/', $line, $pieces)) {
                $mem = $pieces[1];
                break;
            }
        }
        fclose($fh);
        return $this->formatBytes($mem);
    }

    public function getMemoryUsage() {
        $free = shell_exec('free');
        $free = (string) trim($free);
        $free_arr = explode("\n", $free);
        $mem = explode(" ", $free_arr[1]);
        $mem = array_filter($mem);
        $mem = array_merge($mem);
        $memory_usage = $mem[2] / $mem[1] * 100;
        return $memory_usage;
    }

    public function formatBytes($size, $precision = 2) {
        $base = log($size, 1024);
        $suffixes = array('', 'KB', 'MB', 'GB', 'TB');

        return round(pow(1024, $base - floor($base)), $precision) . ' ' . $suffixes[floor($base)];
    }

    public function getDiskSpaceUsage() {
        return $this->formatBytes(disk_total_space("/") - disk_free_space("/"));
    }

}
