class ProductsController < ApplicationController
  respond_to :html, :xml

  def index
    @products = Product.all
    respond_with @products
  end

  def show
    @product = Product.find(params[:id])
    @test_case = TestCase.new
    respond_with @product, @test_case
  end

  def new
    @product = Product.new
    respond_with @product
  end

  def edit
    @product = Product.find(params[:id])
    respond_with @product
  end

  def create
    @product = Product.new(params[:product])
    if @product.save
      flash[:notice] = 'Product created.'
    else
      flash[:notice] = 'Failed to create product.'
    end
    respond_with @product
  end

  def update
    @product = Product.find(params[:id])
    if @product.update_attributes(params[:product])
      flash[:notice] = 'Product updated'
    else
      flash[:notice] = 'Failed to update product.'
    end
    respond_with @product
  end

  def destroy
    @product = Product.find(params[:id])
    @product.destroy
    flash[:notice] = 'Product removed.'
    respond_with @product
  end
end
