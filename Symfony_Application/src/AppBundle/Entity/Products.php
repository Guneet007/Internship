<?php

namespace AppBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Products
 *
 * @ORM\Table(name="products")
 * @ORM\Entity(repositoryClass="AppBundle\Repository\ProductsRepository")
 */
class Products {

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
     * @var float
     *
     * @ORM\Column(name="price", type="float")
     */
    private $price;

    /**
     * @ORM\ManyToMany(targetEntity="Ingredients")
     * @ORM\JoinTable(name="product_ingredient",
     *      joinColumns={@ORM\JoinColumn(name="product_id", referencedColumnName="id")},
     *      inverseJoinColumns={@ORM\JoinColumn(name="ingredient_id", referencedColumnName="id")}
     *      )
     */
    private $ingredients;

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
     * @return Products
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
     * Set price
     *
     * @param float $price
     *
     * @return Products
     */
    public function setPrice($price) {
        $this->price = $price;

        return $this;
    }

    /**
     * Get price
     *
     * @return float
     */
    public function getPrice() {
        return $this->price;
    }

    /**
     * Constructor
     */
    public function __construct()
    {
        $this->ingredients = new \Doctrine\Common\Collections\ArrayCollection();
    }

    /**
     * Add ingredient
     *
     * @param \AppBundle\Entity\Ingredients $ingredient
     *
     * @return Products
     */
    public function addIngredient(\AppBundle\Entity\Ingredients $ingredient)
    {
        $this->ingredients[] = $ingredient;

        return $this;
    }

    /**
     * Remove ingredient
     *
     * @param \AppBundle\Entity\Ingredients $ingredient
     */
    public function removeIngredient(\AppBundle\Entity\Ingredients $ingredient)
    {
        $this->ingredients->removeElement($ingredient);
    }

    /**
     * Get ingredients
     *
     * @return \Doctrine\Common\Collections\Collection
     */
    public function getIngredients()
    {
        return $this->ingredients;
    }
}
