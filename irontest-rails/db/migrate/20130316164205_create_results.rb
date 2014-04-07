class CreateResults < ActiveRecord::Migration
  def change
    create_table :results do |t|
      t.integer :test_case_id
      t.string :result
      t.text :comment

      t.timestamps
    end
  end
end
