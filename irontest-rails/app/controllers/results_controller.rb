class ResultsController < ApplicationController
  before_filter :get_test_case_and_product
  respond_to :html, :xml

  def index
    @results = @test_case.results
    respond_with @results
  end

  def show
    @result = Result.find(params[:id])
    respond_with @product, @test_case, @result
  end

  def new
    @result = @test_case.results.new
    respond_with @product, @test_case
  end

  def create
    @result = @test_case.results.new(params[:result])
    if @result.save
      flash[:notice] = "Result created."
    else
      flash[:notice] = "Failed to create result."
    end
    respond_with @product, @test_case, @result
  end

  private

  def get_test_case_and_product
    @test_case = TestCase.find(params[:test_case_id])
    @product = Product.find(params[:product_id])
  end
end
