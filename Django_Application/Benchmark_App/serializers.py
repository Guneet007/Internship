from rest_framework import serializers

from .models import NutritionalInfo, Ingredients, Product, Order, User


class NutritionalSerializer(serializers.ModelSerializer):
    class Meta:
        model = NutritionalInfo
        fields = ('key', 'value', 'units')


class IngredientsSerializer(serializers.ModelSerializer):
    nutritions = NutritionalSerializer(many=True)

    class Meta:
        model = Ingredients
        fields = ('name', 'nutritions')

    def create(self, validated_data):
        nutritional_data = validated_data.pop('nutritional_data')
        ingredient = Ingredients.objects.create(**validated_data)
        for nutrition in nutritional_data:
            NutritionalInfo.objects.create(ingredients=ingredient, **nutrition)
        return ingredient


class ProductSerializer(serializers.ModelSerializer):
    ingredients = IngredientsSerializer(many=True)

    class Meta:
        model = Product
        fields = ('id', 'name', 'price', 'ingredients')

    def create(self, validated_data):
        ingredients = validated_data.pop('ingredients', None)
        product = Product.objects.create(**validated_data)

        if ingredients is not None:
            for ingredient in ingredients:
                nutritions = ingredient.pop('nutritions', None)
                ingredient_new = Ingredients.objects.create(product=product, **ingredient)
                if nutritions is not None:
                    for nutrition in nutritions:
                        NutritionalInfo.objects.create(ingredients=ingredient_new, **nutrition)

        return product


class UserSerializers(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ('__all__')


class OrderSerializer(serializers.ModelSerializer):
    product = ProductSerializer(many=True)

    class Meta:
        model = Order
        fields = ('__all__')


class CreateOrderSerializer(serializers.ModelSerializer):
    user = serializers.ReadOnlyField(source=User.id)
    product = ProductSerializer(many=True, read_only=True)

    class Meta:
        model = Order
        fields = ('__all__')