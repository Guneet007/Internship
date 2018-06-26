# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from decimal import Decimal

from django.db import models

from django.utils import timezone


class Product(models.Model):
    name = models.CharField(max_length=100)
    price = models.DecimalField(max_digits=20, decimal_places=2, default=Decimal(0.00))

    def __unicode__(self):
        return '%s' % (self.name)


class Ingredients(models.Model):
    name = models.CharField(max_length=100)
    product = models.ForeignKey(Product, related_name='ingredients', on_delete=models.CASCADE)

    class Meta:
        unique_together = ('name', 'product')
        ordering = ['name']

    def __unicode__(self):
        return '%s' % (self.name)


class NutritionalInfo(models.Model):
    ingredients = models.ForeignKey(Ingredients, related_name='nutritions', on_delete=models.CASCADE)
    key = models.CharField(max_length=100)
    value = models.CharField(max_length=100)
    units = models.CharField(max_length=100)

    class Meta:
        unique_together = ('ingredients', 'key')
        ordering = ['ingredients']

    def __unicode__(self):
        return '%s: %s' % (self.ingredients, self.key)


class User(models.Model):
    name = models.CharField(max_length=200)
    email = models.EmailField(max_length=200)
    phone = models.BigIntegerField()
    address = models.CharField(null=True, max_length=200)

    def __unicode__(self):
        return '%s' % (self.name)


class Order(models.Model):
    user = models.ForeignKey(User, related_name='user', on_delete=models.CASCADE)
    product = models.ManyToManyField(Product, related_name='product', related_query_name='product')
    price = models.DecimalField(max_digits=20, decimal_places=2, default=Decimal(0.00))
    date = models.DateField(default=timezone.now)

    class Meta:
        ordering = ['id']

    def __unicode__(self):
        return '%s' % (self.id)