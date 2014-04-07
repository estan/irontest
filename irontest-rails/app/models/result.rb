class Result < ActiveRecord::Base
  belongs_to :test_case
  attr_accessible :comment, :result, :test_case_id, :tester, :test_case, :created_at
  validates_presence_of :result, :test_case_id, :tester

  RESULTS = ['Passed', 'Failed']
  validates_inclusion_of :result, :in => RESULTS, :message => "{{value}} must be in #{RESULTS.join ','}"

  default_scope order('created_at DESC')
end
