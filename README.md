# WeaselList Console
## Simplified WeaselList console application

This is an SpringBoot CommandLineRunner based application written in Java 12.
It requires a MongoDB database.

When running this application it will search in **C:\weasellist** for a **weasellist.properties** file.
If no file found it will create a new one, ask for some connection parameters and write them as a uri in the created file.
> The result should be something like this: ***mongodb://localhost:27017/weaseldb***

### Available commands:

- Exit -> Exits the application.

- Help -> Displays info about the available commands. If a command name is provided it will display info about that command

- Login -> Asks for a valid auth and logs into the application

- Register -> Registers a new user in the database and logs it in

- View -> Shows the list content as a table.
	 If no list name specified it will show the list of available lists.
	If the name specified is 'tags' it will show the available tags.
	
- Add -> Adds a new element into the database. This element can be either a list, an item in a list, or a tag. 
	It must be specified the element type in the command (list/item/tag).
	
- Remove -> Removes an element from the databaseThis element can be either a list, an item in a list, or a tag. 
	It must be specified the element type in the command (list/item/tag).
	
- Edit -> Edits an element of the database. This element can be either a list, an item in a list, a tag or the user. 
	It must be specified the element type in the command (list/item/tag/user).
	
- Move -> Moves an item to another list.
	Additional arguments are 'item' 'from' 'to' in that order.
	
- Mongo -> Changes the database connection uri.
