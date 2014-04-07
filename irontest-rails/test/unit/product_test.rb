require 'test_helper'

class ProductTest < ActiveSupport::TestCase
  test "product attributes must not be empty" do
    product = Product.new
    assert product.invalid?
    assert product.errors[:name].any?
  end
  test "product names must be unique" do
    first_product = Product.new
    first_product.name = 'myproduct'
    first_product.save
    second_product = Product.new
    second_product.name = 'myproduct'
    assert second_product.invalid?
    assert second_product.errors[:name].any?
  end
end
