<?php

namespace AppBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Orders
 *
 * @ORM\Table(name="orders")
 * @ORM\Entity(repositoryClass="AppBundle\Repository\OrdersRepository")
 */
class Orders {

    /**
     * @var int
     *
     * @ORM\Column(name="id", type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    private $id;

    /**
     * @var int
     *
     * @ORM\Column(name="userId", type="integer")
     */
    private $userId;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="date", type="datetime")
     */
    private $date;

    /**
     * @ORM\ManyToMany(targetEntity="Products")
     * @ORM\JoinTable(name="order_product",
     *      joinColumns={@ORM\JoinColumn(name="order_id", referencedColumnName="id")},
     *      inverseJoinColumns={@ORM\JoinColumn(name="product_id", referencedColumnName="id")}
     *      )
     */
    private $products;

    /**
     * Get id
     *
     * @return int
     */
    public function getId() {
        return $this->id;
    }

    /**
     * Set userId
     *
     * @param integer $userId
     *
     * @return Orders
     */
    public function setUserId($userId) {
        $this->userId = $userId;

        return $this;
    }

    /**
     * Get userId
     *
     * @return int
     */
    public function getUserId() {
        return $this->userId;
    }

    /**
     * Set date
     *
     * @param \DateTime $date
     *
     * @return Orders
     */
    public function setDate($date) {
        $this->date = $date;

        return $this;
    }

    /**
     * Get date
     *
     * @return \DateTime
     */
    public function getDate() {
        return $this->date;
    }

    /**
     * Constructor
     */
    public function __construct()
    {
        $this->products = new \Doctrine\Common\Collections\ArrayCollection();
    }

    /**
     * Add product
     *
     * @param \AppBundle\Entity\Products $product
     *
     * @return Orders
     */
    public function addProduct(\AppBundle\Entity\Products $product)
    {
        $this->products[] = $product;

        return $this;
    }

    /**
     * Remove product
     *
     * @param \AppBundle\Entity\Products $product
     */
    public function removeProduct(\AppBundle\Entity\Products $product)
    {
        $this->products->removeElement($product);
    }

    /**
     * Get products
     *
     * @return \Doctrine\Common\Collections\Collection
     */
    public function getProducts()
    {
        return $this->products;
    }
}
