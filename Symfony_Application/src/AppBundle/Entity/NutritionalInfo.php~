<?php

namespace AppBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * NutritionalInfo
 *
 * @ORM\Table(name="nutritional_info")
 * @ORM\Entity(repositoryClass="AppBundle\Repository\NutritionalInfoRepository")
 */
class NutritionalInfo {

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
     * @var string
     *
     * @ORM\Column(name="value", type="string", length=255)
     */
    private $value;

    /**
     * @var string
     *
     * @ORM\Column(name="unit", type="string", length=255)
     */
    private $unit;

    /**
     * @ORM\ManyToOne(targetEntity="Ingredients", inversedBy="nutritionalInfo")
     */
    protected $ingredient;

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
     * @return NutritionalInfo
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
     * Set value
     *
     * @param string $value
     *
     * @return NutritionalInfo
     */
    public function setValue($value) {
        $this->value = $value;

        return $this;
    }

    /**
     * Get value
     *
     * @return string
     */
    public function getValue() {
        return $this->value;
    }

    /**
     * Set unit
     *
     * @param string $unit
     *
     * @return NutritionalInfo
     */
    public function setUnit($unit) {
        $this->unit = $unit;

        return $this;
    }

    /**
     * Get unit
     *
     * @return string
     */
    public function getUnit() {
        return $this->unit;
    }

    /**
     * Set ingredient
     *
     * @param \AppBundle\Entity\Ingredients $ingredient
     *
     * @return NutritionalInfo
     */
    public function setIngredient(\AppBundle\Entity\Ingredients $ingredient = null) {
        $this->ingredient = $ingredient;

        return $this;
    }

    /**
     * Get ingredient
     *
     * @return \AppBundle\Entity\Ingredients
     */
    public function getIngredient() {
        return $this->ingredient;
    }

}
