# WeaselList Console
## Simplified WeaselList console application

This is an SpringBoot CommandLineRunner based application written in Java 12.
It requires a MongoDB database.

When running this application it will search in **C:\weasellist** for a **weasellist.properties** file.
If no file found it will create a new one, ask for some connection parameters and write them as a uri in the created file.
> The result should be something like this: ***mongodb://localhost:27017/weaseldb***

### What is WeaselList?

Given that there are so many online services for different multimedia content we may want to list the content we have to see, play, read ... as well as that we have already seen, played ... And more listings at will. 

The problem with this is that each platform has its own lists for its content, but none of them includes the rest and we end up having lists everywhere.

WeaselList is a project that aims to offer the possibility to create and personalize your lists in a single platform along with extra functionalities.

### So, what is WeaselList Console?

WeaselList is planed to be a web application and it's development is currently on-hold, the first version is almost completed but the front-end is not fully developed.

WeaselList Console is a lighter version on WeaselList, with only the main (CRUD) functionalities and it's executed as a CMD, not as user-friendly as a web page. It is also easier to develop so it can be used while the main project is a WIP.

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
