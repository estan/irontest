class TestCasesController < ApplicationController
  before_filter :get_product
  respond_to :html, :xml

  def index
    @test_cases = @product.test_cases
    respond_with @test_cases
  end

  def show
    @test_case = @product.test_cases.find(params[:id])
    @result = Result.new
    respond_with @test_case, @result
  end

  def new
    @test_case = @product.test_cases.new
    respond_with @product, @test_case
  end

  def edit
    @test_case = @product.test_cases.find(params[:id])
    respond_with @test_case
  end

  def create
    @test_case = @product.test_cases.new(params[:test_case])
    if @test_case.save
      flash[:notice] = "Test case created."
    else
      flash[:notice] = "Failed to save test case."
    end
    respond_with @product, @test_case
  end

  def update
    @test_case = @product.test_cases.find(params[:id])
    if @test_case.update_attributes(params[:test_case])
      flash[:notice] = "Test case updated."
    else
      flash[:notice] = "Failed to update test case."
    end
    respond_with @product, @test_case
  end

  def destroy
    @test_case = @product.test_cases.find(params[:id])
    @test_case.destroy
    flash[:notice] = "Test case removed."
    respond_with @product
  end

  private

  def get_product
    @product = Product.find(params[:product_id])
  end
end
