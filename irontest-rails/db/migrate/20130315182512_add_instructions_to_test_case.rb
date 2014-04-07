class AddInstructionsToTestCase < ActiveRecord::Migration
  def change
    add_column :test_cases, :instructions, :text
  end
end
