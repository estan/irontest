class AddTesterToResult < ActiveRecord::Migration
  def change
    add_column :results, :tester, :string
  end
end
