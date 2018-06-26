<?php

namespace AppBundle\Entity;

use Doctrine\ORM\Mapping as ORM;
use Doctrine\Common\Collections\ArrayCollection;

/**
 * Ingredients
 *
 * @ORM\Table(name="ingredients")
 * @ORM\Entity(repositoryClass="AppBundle\Repository\IngredientsRepository")
 */
class Ingredients {

    /**
     * @var int
     *
     * @ORM\Column(name="id", type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    private $id;

    /**
     * @var string
     *
     * @ORM\Column(name="name", type="string", length=255)
     */
    private $name;

    /**
     * @ORM\OneToMany(targetEntity="NutritionalInfo", mappedBy="ingredient")
     */
    private $nutritionalInfo;

    /**
     * Get id
     *
     * @return int
     */
    public function getId() {
        return $this->id;
    }

    /**
     * Set name
     *
     * @param string $name
     *
     * @return Ingredients
     */
    public function setName($name) {
        $this->name = $name;

        return $this;
    }

    /**
     * Get name
     *
     * @return string
     */
    public function getName() {
        return $this->name;
    }

    /**
     * Constructor
     */
    public function __construct() {
        $this->nutritionalInfo = new \Doctrine\Common\Collections\ArrayCollection();
    }

    /**
     * Add nutritionalInfo
     *
     * @param \AppBundle\Entity\NutritionalInfo $nutritionalInfo
     *
     * @return Ingredients
     */
    public function addNutritionalInfo(\AppBundle\Entity\NutritionalInfo $nutritionalInfo) {
        $this->nutritionalInfo[] = $nutritionalInfo;

        return $this;
    }

    /**
     * Remove nutritionalInfo
     *
     * @param \AppBundle\Entity\NutritionalInfo $nutritionalInfo
     */
    public function removeNutritionalInfo(\AppBundle\Entity\NutritionalInfo $nutritionalInfo) {
        $this->nutritionalInfo->removeElement($nutritionalInfo);
    }

    /**
     * Get nutritionalInfo
     *
     * @return \Doctrine\Common\Collections\Collection
     */
    public function getNutritionalInfo() {
        return $this->nutritionalInfo;
    }

}
