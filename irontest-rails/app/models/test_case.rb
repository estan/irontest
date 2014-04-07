class TestCase < ActiveRecord::Base
  belongs_to :product
  has_many :results, :dependent => :destroy
  attr_accessible :product_id, :title, :instructions, :results, :product
  validates_presence_of :title, :product_id, :instructions
  default_scope order('title ASC')

  def failed
    results.where("result = 'Failed'").count
  end

  def passed
    results.where("result = 'Passed'").count
  end

  def status
    return results.first == nil ? 'N/A' : results.first.result
  end
end
