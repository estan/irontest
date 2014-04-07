# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rake db:seed (or created alongside the db with db:setup).
#
# Examples:
#
#   cities = City.create([{ name: 'Chicago' }, { name: 'Copenhagen' }])
#   Mayor.create(name: 'Emanuel', city: cities.first)

Result.delete_all
TestCase.delete_all
Product.delete_all

def make_results(test_case)
  Random.rand(10..15).times do
    Result.create(
      {
        test_case: test_case,
        result: ['Passed', 'Failed'].sample,
        tester: ['Elvis', 'Bill Gates', 'Linus Torvalds'].sample,
        comment: '',
        created_at: (Random.rand(0..5).weeks + Random.rand(20..200).hours + Random.rand(10..30).minutes).ago
      }
    )
  end
end

simulator = Product.create(
    {
      name: 'simulator',
      description: 'The server simulator simulates a server and allows for easier testing of client features.'
    }
)
add_msg_tc = TestCase.create(
  {
    title: 'Adding a message',
    instructions: '
1. Start the application.
2. Go to *Message->Add* in the menu and pick a message type.
3. *Check* that the message is added.
4. *Check* that the message is selected in the message list.
5. *Check* that the message is selected on the map.
6. *Check* that the message fields are visible in the *Field Editor*.
',
    product: simulator
  }
)
make_results add_msg_tc

del_msg_tc = TestCase.create(
  {
    title: 'Removing a message',
    instructions: '
# Using Menus
1. Start the application.
2. Create a message.
3. Select the message.
5. Go to *Message->Remove* in the menus.
6. *Check* that the message is deleted.

# Using Shortcut
1. Start the application.
2. Create a message.
3. Select the message.
5. Press *Escape* on the keyboard.
6. *Check* that the message is deleted.
',
    product: simulator
  }
)
make_results del_msg_tc

proxy = Product.create(
    {
      name: 'proxy',
      description: 'The proxy sits between the server and its clients, talking the old ASCII based protocol with the server and the new Protocol Buffers based protocol with the clients..'
    }
)

irontest = Product.create(
    {
      name: 'irontest',
      description: "IronTest is a small web application for creating, managing and running manual user tests. It's written using the Ruby on Rails framework."
    }
)

