class Product < ActiveRecord::Base
  has_many :test_cases, :dependent => :destroy
  attr_accessible :name, :description, :test_cases
  validates :name, presence: true, uniqueness: true
  default_scope order('name ASC')

  def passing
    # Return the number of tests cases that are currently passing.
    test_cases.joins(:results)
      .select("test_cases.id, test_cases.title, results.created_at")
      .where("results.created_at = (SELECT MAX(created_at) FROM results r2
             WHERE r2.test_case_id = test_cases.id) AND results.result = 'Passed'").count
  end
end
