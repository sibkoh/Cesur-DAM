CREATE DATABASE NorthwindNEW;
USE NorthwindNEW;
CREATE TABLE Empleados (
	ID_Empleado int  NOT NULL,
	Apellido varchar (20) NOT NULL ,
	Nombre varchar (10) NOT NULL ,
	Titulo varchar (30) NULL ,
	Cortesia varchar (25) NULL ,
	FecNacimiento date NULL ,
	FecContratacion date NULL ,
	Direccion varchar (60) NULL ,
	Ciudad varchar (15) NULL ,
	Region varchar (15) NULL ,
	CodPostal varchar (10) NULL ,
	Pais varchar (15) NULL ,
	Tlf1 varchar (24) NULL ,
	Extension varchar (4) NULL ,
	Notas varchar (1000) NULL ,
	RefiereA int NULL ,
	Salario float(8,2),
	CONSTRAINT PK_Empleados PRIMARY KEY  CLUSTERED 
	(
		ID_Empleado
	)
);

CREATE TABLE Categorias (
	ID_Categoria int NOT NULL AUTO_INCREMENT,
	Nombre varchar (15) NOT NULL ,
	Descripcion varchar (150) NULL ,
	CONSTRAINT PK_Categorias PRIMARY KEY  CLUSTERED 
	(
		ID_Categoria
	)
);

CREATE TABLE Clientes (
	ID_Cliente varchar(5) NOT NULL,
	Nombre varchar (40) NOT NULL ,
	Contacto varchar (30) NULL ,
	TituloContacto varchar (30) NULL ,
	Direccion varchar (60) NULL ,
	Ciudad varchar (15) NULL ,
	Region varchar (15) NULL ,
	CodPostal varchar (10) NULL ,
	Pais varchar (15) NULL ,
	Telefono varchar (24) NULL ,
	Fax varchar (24) NULL ,
	CONSTRAINT PK_Clientes PRIMARY KEY  CLUSTERED 
	(
		ID_Cliente
	)
);

CREATE TABLE Expedidores (
	ID_Expedidor int  NOT NULL AUTO_INCREMENT,
	Nombre varchar (40) NOT NULL ,
	Telefono varchar (24) NULL ,
	CONSTRAINT PK_Expedidores PRIMARY KEY  CLUSTERED 
	(
		ID_Expedidor
	)
);


CREATE TABLE Proveedores (
	ID_Proveedor int NOT NULL AUTO_INCREMENT,
	Nombre varchar (40) NOT NULL ,
	Contacto varchar (30) NULL ,
	TituloContacto varchar (30) NULL ,
	Direccion varchar (60) NULL ,
	Ciudad varchar (15) NULL ,
	Region varchar (15) NULL ,
	CodPostal varchar (10) NULL ,
	Pais varchar (15) NULL ,
	Telefono varchar (24) NULL ,
	Fax varchar (24) NULL ,
	Web varchar (150) NULL ,
	CONSTRAINT PK_Proveedores PRIMARY KEY  CLUSTERED 
	(
		ID_Proveedor
	)
);

CREATE TABLE Pedidos (
	ID_Pedido int  NOT NULL,
	ID_Cliente varchar(5) NULL ,
	ID_Empleado int NULL ,
	FechaPedido date NULL ,
	FechaRequerida date NULL ,
	FechaExpedicion date NULL ,
	FormaExpedicion int NULL ,
	/*Freight money NULL CONSTRAINT DF_Pedidos_Freight DEFAULT (0),*/
	NombreExpedicion varchar (40) NULL ,
	DireccionExpedicion varchar (60) NULL ,
	CiudadExpedicion varchar (15) NULL ,
	RegionExpedicion varchar (15) NULL ,
	CodPostalExpedicion varchar (10) NULL ,
	PaisExpedicion varchar (15) NULL ,
	CONSTRAINT PK_Pedidos PRIMARY KEY  CLUSTERED 
	(
		ID_Pedido
	)
);

CREATE TABLE Productos (
	ID_Producto int NOT NULL AUTO_INCREMENT,
	Nombre varchar (40) NOT NULL ,
	ID_Proveedor int NULL ,
	ID_Categoria int NULL ,
	CantidadPorUnidad varchar (20) NULL ,
	PrecioUnitario decimal(6,2),
	CONSTRAINT PK_Productos PRIMARY KEY  CLUSTERED 
	(
		ID_Producto
	)
);

CREATE TABLE DetallePedido (
	ID_Pedido int NOT NULL ,
	ID_Producto int NOT NULL ,
	PrecioUnitario decimal(6,2) NOT NULL ,
	Cantidad smallint NOT NULL ,
	Descuento decimal(4,2) NOT NULL ,
	CONSTRAINT PK_Order_Details PRIMARY KEY  CLUSTERED 
	(
		ID_Pedido,
		ID_Producto
	)
);


ALTER TABLE Pedidos ADD FOREIGN KEY (ID_Cliente) REFERENCES Clientes(ID_Cliente);
ALTER TABLE Pedidos ADD FOREIGN KEY (ID_Empleado) REFERENCES Empleados(ID_Empleado);
ALTER TABLE Pedidos ADD FOREIGN KEY (FormaExpedicion) REFERENCES Expedidores(ID_Expedidor);
ALTER TABLE Productos ADD FOREIGN KEY (ID_Categoria) REFERENCES Categorias(ID_Categoria);
ALTER TABLE Productos ADD FOREIGN KEY (ID_Proveedor) REFERENCES Proveedores(ID_Proveedor);
ALTER TABLE DetallePedido ADD FOREIGN KEY (ID_Pedido) REFERENCES Pedidos(ID_Pedido);
ALTER TABLE DetallePedido ADD FOREIGN KEY (ID_Producto) REFERENCES Productos(ID_Producto);




INSERT INTO Categorias(Nombre,Descripcion) VALUES('Beverages','Soft drinks, coffees, teas, beers, and ales');
INSERT INTO Categorias(Nombre,Descripcion) VALUES('Condiments','Sweet and savory sauces, relishes, spreads, and seasonings');
INSERT INTO Categorias(Nombre,Descripcion) VALUES('Confections','Desserts, candies, and sweet breads');
INSERT INTO Categorias(Nombre,Descripcion) VALUES('Dairy Productos','Cheeses');
INSERT INTO Categorias(Nombre,Descripcion) VALUES('Grains/Cereals','Breads, crackers, pasta, and cereal');
INSERT INTO Categorias(Nombre,Descripcion) VALUES('Meat/Poultry','Prepared meats');
INSERT INTO Categorias(Nombre,Descripcion) VALUES('Produce','Dried fruit and bean curd');
INSERT INTO Categorias(Nombre,Descripcion) VALUES('Seafood','Seaweed and fish');

INSERT INTO Clientes VALUES('ALFKI','Alfreds Futterkiste','Maria Anders','Sales Representative','Obere Str. 57','Berlin',NULL,'12209','Germany','030-0074321','030-0076545');
INSERT INTO Clientes VALUES('ANATR','Ana Trujillo Emparedados y helados','Ana Trujillo','Owner','Avda. de la Constitución 2222','México D.F.',NULL,'05021','Mexico','(5) 555-4729','(5) 555-3745');
INSERT INTO Clientes VALUES('ANTON','Antonio Moreno Taquería','Antonio Moreno','Owner','Mataderos  2312','México D.F.',NULL,'05023','Mexico','(5) 555-3932',NULL);
INSERT INTO Clientes VALUES('AROUT','Around the Horn','Thomas Hardy','Sales Representative','120 Hanover Sq.','London',NULL,'WA1 1DP','UK','(171) 555-7788','(171) 555-6750');
INSERT INTO Clientes VALUES('BERGS','Berglunds snabbköp','Christina Berglund','Order Administrator','Berguvsvägen  8','Luleå',NULL,'S-958 22','Sweden','0921-12 34 65','0921-12 34 67');
INSERT INTO Clientes VALUES('BLAUS','Blauer See Delikatessen','Hanna Moos','Sales Representative','Forsterstr. 57','Mannheim',NULL,'68306','Germany','0621-08460','0621-08924');
INSERT INTO Clientes VALUES('BLONP','Blondesddsl père et fils','Frédérique Citeaux','Marketing Manager','24, place Kléber','Strasbourg',NULL,'67000','France','88.60.15.31','88.60.15.32');
INSERT INTO Clientes VALUES('BOLID','Bólido Comidas preparadas','Martín Sommer','Owner','C/ Araquil, 67','Madrid',NULL,'28023','Spain','(91) 555 22 82','(91) 555 91 99');
INSERT INTO Clientes VALUES('BONAP','Bon app''','Laurence Lebihan','Owner','12, rue des Bouchers','Marseille',NULL,'13008','France','91.24.45.40','91.24.45.41');
INSERT INTO Clientes VALUES('BOTTM','Bottom-Dollar Markets','Elizabeth Lincoln','Accounting Manager','23 Tsawassen Blvd.','Tsawassen','BC','T2F 8M4','Canada','(604) 555-4729','(604) 555-3745');
INSERT INTO Clientes VALUES('BSBEV','Bs Beverages','Victoria Ashworth','Sales Representative','Fauntleroy Circus','London',NULL,'EC2 5NT','UK','(171) 555-1212',NULL);
INSERT INTO Clientes VALUES('CACTU','Cactus Comidas para llevar','Patricio Simpson','Sales Agent','Cerrito 333','Buenos Aires',NULL,'1010','Argentina','(1) 135-5555','(1) 135-4892');
INSERT INTO Clientes VALUES('CENTC','Centro comercial Moctezuma','Francisco Chang','Marketing Manager','Sierras de Granada 9993','México D.F.',NULL,'05022','Mexico','(5) 555-3392','(5) 555-7293');
INSERT INTO Clientes VALUES('CHOPS','Chop-suey Chinese','Yang Wang','Owner','Hauptstr. 29','Bern',NULL,'3012','Switzerland','0452-076545',NULL);
INSERT INTO Clientes VALUES('COMMI','Comércio Mineiro','Pedro Afonso','Sales Associate','Av. dos Lusíadas, 23','Sao Paulo','SP','05432-043','Brazil','(11) 555-7647',NULL);
INSERT INTO Clientes VALUES('CONSH','Consolidated Holdings','Elizabeth Brown','Sales Representative','Berkeley Gardens 12  Brewery','London',NULL,'WX1 6LT','UK','(171) 555-2282','(171) 555-9199');
INSERT INTO Clientes VALUES('DRACD','Drachenblut Delikatessen','Sven Ottlieb','Order Administrator','Walserweg 21','Aachen',NULL,'52066','Germany','0241-039123','0241-059428');
INSERT INTO Clientes VALUES('DUMON','Du monde entier','Janine Labrune','Owner','67, rue des Cinquante Otages','Nantes',NULL,'44000','France','40.67.88.88','40.67.89.89');
INSERT INTO Clientes VALUES('EASTC','Eastern Connection','Ann Devon','Sales Agent','35 King George','London',NULL,'WX3 6FW','UK','(171) 555-0297','(171) 555-3373');
INSERT INTO Clientes VALUES('ERNSH','Ernst Handel','Roland Mendel','Sales Manager','Kirchgasse 6','Graz',NULL,'8010','Austria','7675-3425','7675-3426');
INSERT INTO Clientes VALUES('FAMIA','Familia Arquibaldo','Aria Cruz','Marketing Assistant','Rua Orós, 92','Sao Paulo','SP','05442-030','Brazil','(11) 555-9857',NULL);
INSERT INTO Clientes VALUES('FISSA','FISSA Fabrica Inter. Salchichas S.A.','Diego Roel','Accounting Manager','C/ Moralzarzal, 86','Madrid',NULL,'28034','Spain','(91) 555 94 44','(91) 555 55 93');
INSERT INTO Clientes VALUES('FOLIG','Folies gourmandes','Martine Rancé','Assistant Sales Agent','184, chaussée de Tournai','Lille',NULL,'59000','France','20.16.10.16','20.16.10.17');
INSERT INTO Clientes VALUES('FOLKO','Folk och fä HB','Maria Larsson','Owner','Åkergatan 24','Bräcke',NULL,'S-844 67','Sweden','0695-34 67 21',NULL);
INSERT INTO Clientes VALUES('FRANK','Frankenversand','Peter Franken','Marketing Manager','Berliner Platz 43','München',NULL,'80805','Germany','089-0877310','089-0877451');
INSERT INTO Clientes VALUES('FRANR','France restauration','Carine Schmitt','Marketing Manager','54, rue Royale','Nantes',NULL,'44000','France','40.32.21.21','40.32.21.20');
INSERT INTO Clientes VALUES('FRANS','Franchi S.p.A.','Paolo Accorti','Sales Representative','Via Monte Bianco 34','Torino',NULL,'10100','Italy','011-4988260','011-4988261');
INSERT INTO Clientes VALUES('FURIB','Furia Bacalhau e Frutos do Mar','Lino Rodriguez','Sales Manager','Jardim das rosas n. 32','Lisboa',NULL,'1675','Portugal','(1) 354-2534','(1) 354-2535');
INSERT INTO Clientes VALUES('GALED','Galería del gastrónomo','Eduardo Saavedra','Marketing Manager','Rambla de Cataluña, 23','Barcelona',NULL,'08022','Spain','(93) 203 4560','(93) 203 4561');
INSERT INTO Clientes VALUES('GODOS','Godos Cocina Típica','José Pedro Freyre','Sales Manager','C/ Romero, 33','Sevilla',NULL,'41101','Spain','(95) 555 82 82',NULL);
INSERT INTO Clientes VALUES('GOURL','Gourmet Lanchonetes','André Fonseca','Sales Associate','Av. Brasil, 442','Campinas','SP','04876-786','Brazil','(11) 555-9482',NULL);
INSERT INTO Clientes VALUES('GREAL','Great Lakes Food Market','Howard Snyder','Marketing Manager','2732 Baker Blvd.','Eugene','OR','97403','USA','(503) 555-7555',NULL);
INSERT INTO Clientes VALUES('GROSR','GROSELLA-Restaurante','Manuel Pereira','Owner','5ª Ave. Los Palos Grandes','Caracas','DF','1081','Venezuela','(2) 283-2951','(2) 283-3397');
INSERT INTO Clientes VALUES('HANAR','Hanari Carnes','Mario Pontes','Accounting Manager','Rua do Paço, 67','Rio de Janeiro','RJ','05454-876','Brazil','(21) 555-0091','(21) 555-8765');
INSERT INTO Clientes VALUES('HILAA','HILARION-Abastos','Carlos Hernández','Sales Representative','Carrera 22 con Ave. Carlos Soublette #8-35','San Cristóbal','Táchira','5022','Venezuela','(5) 555-1340','(5) 555-1948');
INSERT INTO Clientes VALUES('HUNGC','Hungry Coyote Import Store','Yoshi Latimer','Sales Representative','Ciudad Center Plaza 516 Main St.','Elgin','OR','97827','USA','(503) 555-6874','(503) 555-2376');
INSERT INTO Clientes VALUES('HUNGO','Hungry Owl All-Night Grocers','Patricia McKenna','Sales Associate','8 Johnstown Road','Cork','Co. Cork',NULL,'Ireland','2967 542','2967 3333');
INSERT INTO Clientes VALUES('ISLAT','Island Trading','Helen Bennett','Marketing Manager','Garden House Crowther Way','Cowes','Isle of Wight','PO31 7PJ','UK','(198) 555-8888',NULL);
INSERT INTO Clientes VALUES('KOENE','Königlich Essen','Philip Cramer','Sales Associate','Maubelstr. 90','Brandenburg',NULL,'14776','Germany','0555-09876',NULL);
INSERT INTO Clientes VALUES('LACOR','La corne d''abondance','Daniel Tonini','Sales Representative','67, avenue de l''Europe','Versailles',NULL,'78000','France','30.59.84.10','30.59.85.11');
INSERT INTO Clientes VALUES('LAMAI','La maison d''Asie','Annette Roulet','Sales Manager','1 rue Alsace-Lorraine','Toulouse',NULL,'31000','France','61.77.61.10','61.77.61.11');
INSERT INTO Clientes VALUES('LAUGB','Laughing Bacchus Wine Cellars','Yoshi Tannamuri','Marketing Assistant','1900 Oak St.','Vancouver','BC','V3F 2K1','Canada','(604) 555-3392','(604) 555-7293');
INSERT INTO Clientes VALUES('LAZYK','Lazy K Kountry Store','John Steel','Marketing Manager','12 Orchestra Terrace','Walla Walla','WA','99362','USA','(509) 555-7969','(509) 555-6221');
INSERT INTO Clientes VALUES('LEHMS','Lehmanns Marktstand','Renate Messner','Sales Representative','Magazinweg 7','Frankfurt a.M.',NULL,'60528','Germany','069-0245984','069-0245874');
INSERT INTO Clientes VALUES('LETSS','Lets Stop N Shop','Jaime Yorres','Owner','87 Polk St. Suite 5','San Francisco','CA','94117','USA','(415) 555-5938',NULL);
INSERT INTO Clientes VALUES('LILAS','LILA-Supermercado','Carlos González','Accounting Manager','Carrera 52 con Ave. Bolívar #65-98 Llano Largo','Barquisimeto','Lara','3508','Venezuela','(9) 331-6954','(9) 331-7256');
INSERT INTO Clientes VALUES('LINOD','LINO-Delicateses','Felipe Izquierdo','Owner','Ave. 5 de Mayo Porlamar','I. de Margarita','Nueva Esparta','4980','Venezuela','(8) 34-56-12','(8) 34-93-93');
INSERT INTO Clientes VALUES('LONEP','Lonesome Pine Restaurant','Fran Wilson','Sales Manager','89 Chiaroscuro Rd.','Portland','OR','97219','USA','(503) 555-9573','(503) 555-9646');
INSERT INTO Clientes VALUES('MAGAA','Magazzini Alimentari Riuniti','Giovanni Rovelli','Marketing Manager','Via Ludovico il Moro 22','Bergamo',NULL,'24100','Italy','035-640230','035-640231');
INSERT INTO Clientes VALUES('MAISD','Maison Dewey','Catherine Dewey','Sales Agent','Rue Joseph-Bens 532','Bruxelles',NULL,'B-1180','Belgium','(02) 201 24 67','(02) 201 24 68');
INSERT INTO Clientes VALUES('MEREP','Mère Paillarde','Jean Fresnière','Marketing Assistant','43 rue St. Laurent','Montréal','Québec','H1J 1C3','Canada','(514) 555-8054','(514) 555-8055');
INSERT INTO Clientes VALUES('MORGK','Morgenstern Gesundkost','Alexander Feuer','Marketing Assistant','Heerstr. 22','Leipzig',NULL,'04179','Germany','0342-023176',NULL);
INSERT INTO Clientes VALUES('OCEAN','Océano Atlántico Ltda.','Yvonne Moncada','Sales Agent','Ing. Gustavo Moncada 8585 Piso 20-A','Buenos Aires',NULL,'1010','Argentina','(1) 135-5333','(1) 135-5535');
INSERT INTO Clientes VALUES('OLDWO','Old World Delicatessen','Rene Phillips','Sales Representative','2743 Bering St.','Anchorage','AK','99508','USA','(907) 555-7584','(907) 555-2880');
INSERT INTO Clientes VALUES('OTTIK','Ottilies Käseladen','Henriette Pfalzheim','Owner','Mehrheimerstr. 369','Köln',NULL,'50739','Germany','0221-0644327','0221-0765721');
INSERT INTO Clientes VALUES('PARIS','Paris spécialités','Marie Bertrand','Owner','265, boulevard Charonne','Paris',NULL,'75012','France','(1) 42.34.22.66','(1) 42.34.22.77');
INSERT INTO Clientes VALUES('PERIC','Pericles Comidas clásicas','Guillermo Fernández','Sales Representative','Calle Dr. Jorge Cash 321','México D.F.',NULL,'05033','Mexico','(5) 552-3745','(5) 545-3745');
INSERT INTO Clientes VALUES('PICCO','Piccolo und mehr','Georg Pipps','Sales Manager','Geislweg 14','Salzburg',NULL,'5020','Austria','6562-9722','6562-9723');
INSERT INTO Clientes VALUES('PRINI','Princesa Isabel Vinhos','Isabel de Castro','Sales Representative','Estrada da saúde n. 58','Lisboa',NULL,'1756','Portugal','(1) 356-5634',NULL);
INSERT INTO Clientes VALUES('QUEDE','Que Delícia','Bernardo Batista','Accounting Manager','Rua da Panificadora, 12','Rio de Janeiro','RJ','02389-673','Brazil','(21) 555-4252','(21) 555-4545');
INSERT INTO Clientes VALUES('QUEEN','Queen Cozinha','Lúcia Carvalho','Marketing Assistant','Alameda dos Canàrios, 891','Sao Paulo','SP','05487-020','Brazil','(11) 555-1189',NULL);
INSERT INTO Clientes VALUES('QUICK','QUICK-Stop','Horst Kloss','Accounting Manager','Taucherstraße 10','Cunewalde',NULL,'01307','Germany','0372-035188',NULL);
INSERT INTO Clientes VALUES('RANCH','Rancho grande','Sergio Gutiérrez','Sales Representative','Av. del Libertador 900','Buenos Aires',NULL,'1010','Argentina','(1) 123-5555','(1) 123-5556');
INSERT INTO Clientes VALUES('RATTC','Rattlesnake Canyon Grocery','Paula Wilson','Assistant Sales Representative','2817 Milton Dr.','Albuquerque','NM','87110','USA','(505) 555-5939','(505) 555-3620');
INSERT INTO Clientes VALUES('REGGC','Reggiani Caseifici','Maurizio Moroni','Sales Associate','Strada Provinciale 124','Reggio Emilia',NULL,'42100','Italy','0522-556721','0522-556722');
INSERT INTO Clientes VALUES('RICAR','Ricardo Adocicados','Janete Limeira','Assistant Sales Agent','Av. Copacabana, 267','Rio de Janeiro','RJ','02389-890','Brazil','(21) 555-3412',NULL);
INSERT INTO Clientes VALUES('RICSU','Richter Supermarkt','Michael Holz','Sales Manager','Grenzacherweg 237','Genève',NULL,'1203','Switzerland','0897-034214',NULL);
INSERT INTO Clientes VALUES('ROMEY','Romero y tomillo','Alejandra Camino','Accounting Manager','Gran Vía, 1','Madrid',NULL,'28001','Spain','(91) 745 6200','(91) 745 6210');
INSERT INTO Clientes VALUES('SANTG','Santé Gourmet','Jonas Bergulfsen','Owner','Erling Skakkes gate 78','Stavern',NULL,'4110','Norway','07-98 92 35','07-98 92 47');
INSERT INTO Clientes VALUES('SAVEA','Save-a-lot Markets','Jose Pavarotti','Sales Representative','187 Suffolk Ln.','Boise','ID','83720','USA','(208) 555-8097',NULL);
INSERT INTO Clientes VALUES('SEVES','Seven Seas Imports','Hari Kumar','Sales Manager','90 Wadhurst Rd.','London',NULL,'OX15 4NB','UK','(171) 555-1717','(171) 555-5646');
INSERT INTO Clientes VALUES('SIMOB','Simons bistro','Jytte Petersen','Owner','Vinbæltet 34','Kobenhavn',NULL,'1734','Denmark','31 12 34 56','31 13 35 57');
INSERT INTO Clientes VALUES('SPECD','Spécialités du monde','Dominique Perrier','Marketing Manager','25, rue Lauriston','Paris',NULL,'75016','France','(1) 47.55.60.10','(1) 47.55.60.20');
INSERT INTO Clientes VALUES('SPLIR','Split Rail Beer & Ale','Art Braunschweiger','Sales Manager','P.O. Box 555','Lander','WY','82520','USA','(307) 555-4680','(307) 555-6525');
INSERT INTO Clientes VALUES('SUPRD','Suprêmes délices','Pascale Cartrain','Accounting Manager','Boulevard Tirou, 255','Charleroi',NULL,'B-6000','Belgium','(071) 23 67 22 20','(071) 23 67 22 21');
INSERT INTO Clientes VALUES('THEBI','The Big Cheese','Liz Nixon','Marketing Manager','89 Jefferson Way Suite 2','Portland','OR','97201','USA','(503) 555-3612',NULL);
INSERT INTO Clientes VALUES('THECR','The Cracker Box','Liu Wong','Marketing Assistant','55 Grizzly Peak Rd.','Butte','MT','59801','USA','(406) 555-5834','(406) 555-8083');
INSERT INTO Clientes VALUES('TOMSP','Toms Spezialitäten','Karin Josephs','Marketing Manager','Luisenstr. 48','Münster',NULL,'44087','Germany','0251-031259','0251-035695');
INSERT INTO Clientes VALUES('TORTU','Tortuga Restaurante','Miguel Angel Paolino','Owner','Avda. Azteca 123','México D.F.',NULL,'05033','Mexico','(5) 555-2933',NULL);
INSERT INTO Clientes VALUES('TRADH','Tradição Hipermercados','Anabela Domingues','Sales Representative','Av. Inês de Castro, 414','Sao Paulo','SP','05634-030','Brazil','(11) 555-2167','(11) 555-2168');
INSERT INTO Clientes VALUES('TRAIH','Trail''s Head Gourmet Provisioners','Helvetius Nagy','Sales Associate','722 DaVinci Blvd.','Kirkland','WA','98034','USA','(206) 555-8257','(206) 555-2174');
INSERT INTO Clientes VALUES('VAFFE','Vaffeljernet','Palle Ibsen','Sales Manager','Smagsloget 45','Århus',NULL,'8200','Denmark','86 21 32 43','86 22 33 44');
INSERT INTO Clientes VALUES('VICTE','Victuailles en stock','Mary Saveley','Sales Agent','2, rue du Commerce','Lyon',NULL,'69004','France','78.32.54.86','78.32.54.87');
INSERT INTO Clientes VALUES('VINET','Vins et alcools Chevalier','Paul Henriot','Accounting Manager','59 rue de l''Abbaye','Reims',NULL,'51100','France','26.47.15.10','26.47.15.11');
INSERT INTO Clientes VALUES('WANDK','Die Wandernde Kuh','Rita Müller','Sales Representative','Adenauerallee 900','Stuttgart',NULL,'70563','Germany','0711-020361','0711-035428');
INSERT INTO Clientes VALUES('WARTH','Wartian Herkku','Pirkko Koskitalo','Accounting Manager','Torikatu 38','Oulu',NULL,'90110','Finland','981-443655','981-443655');
INSERT INTO Clientes VALUES('WELLI','Wellington Importadora','Paula Parente','Sales Manager','Rua do Mercado, 12','Resende','SP','08737-363','Brazil','(14) 555-8122',NULL);
INSERT INTO Clientes VALUES('WHITC','White Clover Markets','Karl Jablonski','Owner','305 - 14th Ave. S. Suite 3B','Seattle','WA','98128','USA','(206) 555-4112','(206) 555-4115');
INSERT INTO Clientes VALUES('WILMK','Wilman Kala','Matti Karttunen','Owner/Marketing Assistant','Keskuskatu 45','Helsinki',NULL,'21240','Finland','90-224 8858','90-224 8858');
INSERT INTO Clientes VALUES('WOLZA','Wolski  Zajazd','Zbyszek Piestrzeniewicz','Owner','ul. Filtrowa 68','Warszawa',NULL,'01-012','Poland','(26) 642-7012','(26) 642-7012');


INSERT INTO Empleados VALUES(1, 'Davolio','Nancy', 'Sales Representative','Ms.','1948-12-08 00:00:00','1992-05-01 00:00:00','507 - 20th Ave. E.Apt. 2A'	,'Seattle','WA','98122','USA','(206) 555-9857','5467','Education includes a BA in psychology from Colorado State University in 1970.  She also completed "The Art of the Cold Call."  Nancy is a member of Toastmasters International.',2, 3000.75);
INSERT INTO Empleados VALUES(2, 'Fuller','Andrew','Vice President','Dr.', '1952-02-19 00:00:00','1992-08-14 00:00:00','908 W. Capital Way','Tacoma','WA','98401','USA',	'(206) 555-9482','3457','Andrew received his BTS commercial in 1974 and a Ph.D. in international marketing from the University of Dallas in 1981.  He is fluent in French and Italian and reads German.  He joined the company as a sales representative, was promoted to sales manager in January 1992 and to vice president of sales in March 1993.  Andrew is a member of the Sales Management Roundtable, the Seattle Chamber of Commerce, and the Pacific Rim Importers Association.',	NULL, 1200);
INSERT INTO Empleados VALUES(3,'Leverling','Janet','Sales Representative','Ms.','1963-08-30 00:00:00','1992-04-01 00:00:00','722 Moss Bay Blvd.','Kirkland','WA','98033','USA','(206) 555-3412','3355','Janet has a BS degree in chemistry from Boston College (1984).  She has also completed a certificate program in food retailing management.  Janet was hired as a sales associate in 1991 and promoted to sales representative in February 1992.',2, 2100);
INSERT INTO Empleados VALUES(4,'Peacock','Margaret','Sales Representative','Mrs.',	'1937-09-19 00:00:00','1993-05-03 00:00:00','4110 Old Redmond Rd.',	'Redmond','WA',	'98052','USA','(206) 555-8122','5176','Margaret holds a BA in English literature.',2, 900);
INSERT INTO Empleados VALUES(5,	'Buchanan',	'Steven','Sales Manager','Mr.','1955-03-04 00:00:00','1993-10-17 00:00:00','14 Garrett Hill','London','WA',	'SW1 8JR','UK','(71) 555-4848','3453','Steven Buchanan graduated from St. Andrews University, Scotland, with a BSC degree in 1976.  Upon joining the company as a sales representative in 1992, he spent 6 months in an orientation program at the Seattle office and then .',	2, 1750.65);
INSERT INTO Empleados VALUES(6,'Suyama','Michael','Sales Representative','Mr.','1963-07-02 00:00:00','1993-10-17 00:00:00','Coventry House Miner Rd.','London','CBD','EC2 7JR','UK',	'(71) 555-7773','428','Michael is a graduate of Sussex University .',5, 2350.45);
INSERT INTO Empleados VALUES(7,'King','Robert','Sales Representative','Mr.',	'1960-05-29 00:00:00','1994-01-02 00:00:00','Edgeham Hollow Winchester Way','London', NULL,	'RG1 9SP','UK',	'(71) 555-5598','465','Robert King served in the Peace Corps ',5, 1900);
INSERT INTO Empleados VALUES(8,'Callahan','Laura','Inside Sales', 'Ms.','1958-01-09 00:00:00','1994-03-05 00:00:00','4726 - 11th Ave. N.E.','Seattle','WA','98105','USA','(206) 555-1189','2344','Laura received a BA in psychology',	2, 2500);
INSERT INTO Empleados VALUES(9,'Dodsworth','Anne','Sales Representative','Ms.','1966-01-27 00:00:00','1994-11-15 00:00:00','7 Houndstooth Rd.','London',NULL,	'WG2 7LT','UK','(71) 555-4444','452','Anne has a BA degree in English.',5, 1650.75);

INSERT INTO Proveedores(Nombre,Contacto,TituloContacto,Direccion,Ciudad,Region,CodPostal,Pais,Telefono,Fax,Web) VALUES('Exotic Liquids','Charlotte Cooper','Purchasing Manager','49 Gilbert St.','London',NULL,'EC1 4SD','UK','(171) 555-2222',NULL,NULL);
INSERT INTO Proveedores(Nombre,Contacto,TituloContacto,Direccion,Ciudad,Region,CodPostal,Pais,Telefono,Fax,Web) VALUES('New Orleans Cajun Delights','Shelley Burke','Order Administrator','P.O. Box 78934','New Orleans','LA','70117','USA','(100) 555-4822',NULL,'#CAJUN.HTM#')																														;
INSERT INTO Proveedores(Nombre,Contacto,TituloContacto,Direccion,Ciudad,Region,CodPostal,Pais,Telefono,Fax,Web) VALUES('Grandma Kelly''s Homestead','Regina Murphy','Sales Representative','707 Oxford Rd.','Ann Arbor','MI','48104','USA','(313) 555-5735','(313) 555-3349',NULL)                                                                                                                     ;
INSERT INTO Proveedores(Nombre,Contacto,TituloContacto,Direccion,Ciudad,Region,CodPostal,Pais,Telefono,Fax,Web) VALUES('Tokyo Traders','Yoshi Nagase','Marketing Manager','9-8 Sekimai Musashino-shi','Tokyo',NULL,'100','Japan','(03) 3555-5011',NULL,NULL)                                                                                                                                           ;
INSERT INTO Proveedores(Nombre,Contacto,TituloContacto,Direccion,Ciudad,Region,CodPostal,Pais,Telefono,Fax,Web) VALUES('Cooperativa de Quesos ''Las Cabras''','Antonio del Valle Saavedra','Export Administrator','Calle del Rosal 4','Oviedo','Asturias','33007','Spain','(98) 598 76 54',NULL,NULL)                                                                                                  ;
INSERT INTO Proveedores(Nombre,Contacto,TituloContacto,Direccion,Ciudad,Region,CodPostal,Pais,Telefono,Fax,Web) VALUES('Mayumi''s','Mayumi Ohno','Marketing Representative','92 Setsuko Chuo-ku','Osaka',NULL,'545','Japan','(06) 431-7877',NULL,'Mayumi''s (on the World Wide Web)#http://www.microsoft.com/accessdev/sampleapps/mayumi.htm#')                                                        ;
INSERT INTO Proveedores(Nombre,Contacto,TituloContacto,Direccion,Ciudad,Region,CodPostal,Pais,Telefono,Fax,Web) VALUES('Pavlova, Ltd.','Ian Devling','Marketing Manager','74 Rose St. Moonie Ponds','Melbourne','Victoria','3058','Australia','(03) 444-2343','(03) 444-6588',NULL)                                                                                                                    ;
INSERT INTO Proveedores(Nombre,Contacto,TituloContacto,Direccion,Ciudad,Region,CodPostal,Pais,Telefono,Fax,Web) VALUES('Specialty Biscuits, Ltd.','Peter Wilson','Sales Representative','29 King''s Way','Manchester',NULL,'M14 GSD','UK','(161) 555-4448',NULL,NULL)                                                                                                                                  ;
INSERT INTO Proveedores(Nombre,Contacto,TituloContacto,Direccion,Ciudad,Region,CodPostal,Pais,Telefono,Fax,Web) VALUES('PB Knäckebröd AB','Lars Peterson','Sales Agent','Kaloadagatan 13','Göteborg',NULL,'S-345 67','Sweden','031-987 65 43','031-987 65 91',NULL)                                                                                                                                    ;
INSERT INTO Proveedores(Nombre,Contacto,TituloContacto,Direccion,Ciudad,Region,CodPostal,Pais,Telefono,Fax,Web) VALUES('Refrescos Americanas LTDA','Carlos Diaz','Marketing Manager','Av. das Americanas 12.890','Sao Paulo',NULL,'5442','Brazil','(11) 555 4640',NULL,NULL)                                                                                                                           ;
INSERT INTO Proveedores(Nombre,Contacto,TituloContacto,Direccion,Ciudad,Region,CodPostal,Pais,Telefono,Fax,Web) VALUES('Heli Süßwaren GmbH & Co. KG','Petra Winkler','Sales Manager','Tiergartenstraße 5','Berlin',NULL,'10785','Germany','(010) 9984510',NULL,NULL)                                                                                                                                   ;
INSERT INTO Proveedores(Nombre,Contacto,TituloContacto,Direccion,Ciudad,Region,CodPostal,Pais,Telefono,Fax,Web) VALUES('Plutzer Lebensmittelgroßmärkte AG','Martin Bein','International Marketing Mgr.','Bogenallee 51','Frankfurt',NULL,'60439','Germany','(069) 992755',NULL,'Plutzer (on the World Wide Web)#http://www.microsoft.com/accessdev/sampleapps/plutzer.htm#')                           ;
INSERT INTO Proveedores(Nombre,Contacto,TituloContacto,Direccion,Ciudad,Region,CodPostal,Pais,Telefono,Fax,Web) VALUES('Nord-Ost-Fisch Handelsgesellschaft mbH','Sven Petersen','Coordinator Foreign Markets','Frahmredder 112a','Cuxhaven',NULL,'27478','Germany','(04721) 8713','(04721) 8714',NULL)                                                                                                 ;
INSERT INTO Proveedores(Nombre,Contacto,TituloContacto,Direccion,Ciudad,Region,CodPostal,Pais,Telefono,Fax,Web) VALUES('Formaggi Fortini s.r.l.','Elio Rossi','Sales Representative','Viale Dante, 75','Ravenna',NULL,'48100','Italy','(0544) 60323','(0544) 60603','#FORMAGGI.HTM#')                                                                                                                  ;
INSERT INTO Proveedores(Nombre,Contacto,TituloContacto,Direccion,Ciudad,Region,CodPostal,Pais,Telefono,Fax,Web) VALUES('Norske Meierier','Beate Vileid','Marketing Manager','Hatlevegen 5','Sandvika',NULL,'1320','Norway','(0)2-953010',NULL,NULL)                                                                                                                                                    ;
INSERT INTO Proveedores(Nombre,Contacto,TituloContacto,Direccion,Ciudad,Region,CodPostal,Pais,Telefono,Fax,Web) VALUES('Bigfoot Breweries','Cheryl Saylor','Regional Account Rep.','3400 - 8th Avenue Suite 210','Bend','OR','97101','USA','(503) 555-9931',NULL,NULL)                                                                                                                                 ;
INSERT INTO Proveedores(Nombre,Contacto,TituloContacto,Direccion,Ciudad,Region,CodPostal,Pais,Telefono,Fax,Web) VALUES('Svensk Sjöföda AB','Michael Björn','Sales Representative','Brovallavägen 231','Stockholm',NULL,'S-123 45','Sweden','08-123 45 67',NULL,NULL)                                                                                                                                   ;
INSERT INTO Proveedores(Nombre,Contacto,TituloContacto,Direccion,Ciudad,Region,CodPostal,Pais,Telefono,Fax,Web) VALUES('Aux joyeux ecclésiastiques','Guylène Nodier','Sales Manager','203, Rue des Francs-Bourgeois','Paris',NULL,'75004','France','(1) 03.83.00.68','(1) 03.83.00.62',NULL)                                                                                                           ;
INSERT INTO Proveedores(Nombre,Contacto,TituloContacto,Direccion,Ciudad,Region,CodPostal,Pais,Telefono,Fax,Web) VALUES('New England Seafood Cannery','Robb Merchant','Wholesale Account Agent','Order Processing Dept. 2100 Paul Revere Blvd.','Boston','MA','02134','USA','(617) 555-3267','(617) 555-3389',NULL)                                                                                     ;
INSERT INTO Proveedores(Nombre,Contacto,TituloContacto,Direccion,Ciudad,Region,CodPostal,Pais,Telefono,Fax,Web) VALUES('Leka Trading','Chandra Leka','Owner','471 Serangoon Loop, Suite #402','Singapore',NULL,'0512','Singapore','555-8787',NULL,NULL)                                                                                                                                                ;
INSERT INTO Proveedores(Nombre,Contacto,TituloContacto,Direccion,Ciudad,Region,CodPostal,Pais,Telefono,Fax,Web) VALUES('Lyngbysild','Niels Petersen','Sales Manager','Lyngbysild Fiskebakken 10','Lyngby',NULL,'2800','Denmark','43844108','43844115',NULL)                                                                                                                                            ;
INSERT INTO Proveedores(Nombre,Contacto,TituloContacto,Direccion,Ciudad,Region,CodPostal,Pais,Telefono,Fax,Web) VALUES('Zaanse Snoepfabriek','Dirk Luchte','Accounting Manager','Verkoop Rijnweg 22','Zaandam',NULL,'9999 ZZ','Netherlands','(12345) 1212','(12345) 1210',NULL)                                                                                                                        ;
INSERT INTO Proveedores(Nombre,Contacto,TituloContacto,Direccion,Ciudad,Region,CodPostal,Pais,Telefono,Fax,Web) VALUES('Karkki Oy','Anne Heikkonen','Product Manager','Valtakatu 12','Lappeenranta',NULL,'53120','Finland','(953) 10956',NULL,NULL)                                                                                                                                                    ;
INSERT INTO Proveedores(Nombre,Contacto,TituloContacto,Direccion,Ciudad,Region,CodPostal,Pais,Telefono,Fax,Web) VALUES('G''day, Mate','Wendy Mackenzie','Sales Representative','170 Prince Edward Parade Hunter''s Hill','Sydney','NSW','2042','Australia','(02) 555-5914','(02) 555-4873','G''day Mate (on the World Wide Web)#http://www.microsoft.com/accessdev/sampleapps/gdaymate.htm#')          ;
INSERT INTO Proveedores(Nombre,Contacto,TituloContacto,Direccion,Ciudad,Region,CodPostal,Pais,Telefono,Fax,Web) VALUES('Ma Maison','Jean-Guy Lauzon','Marketing Manager','2960 Rue St. Laurent','Montréal','Québec','H1J 1C3','Canada','(514) 555-9022',NULL,NULL)                                                                                                                                     ;
INSERT INTO Proveedores(Nombre,Contacto,TituloContacto,Direccion,Ciudad,Region,CodPostal,Pais,Telefono,Fax,Web) VALUES('Pasta Buttini s.r.l.','Giovanni Giudici','Order Administrator','Via dei Gelsomini, 153','Salerno',NULL,'84100','Italy','(089) 6547665','(089) 6547667',NULL)                                                                                                                   ;
INSERT INTO Proveedores(Nombre,Contacto,TituloContacto,Direccion,Ciudad,Region,CodPostal,Pais,Telefono,Fax,Web) VALUES('Escargots Nouveaux','Marie Delamare','Sales Manager','22, rue H. Voiron','Montceau',NULL,'71300','France','85.57.00.07',NULL,NULL)                                                                                                                                             ;
INSERT INTO Proveedores(Nombre,Contacto,TituloContacto,Direccion,Ciudad,Region,CodPostal,Pais,Telefono,Fax,Web) VALUES('Gai pâturage','Eliane Noz','Sales Representative','Bat. B 3, rue des Alpes','Annecy',NULL,'74000','France','38.76.98.06','38.76.98.58',NULL)                                                                                                                                   ;
INSERT INTO Proveedores(Nombre,Contacto,TituloContacto,Direccion,Ciudad,Region,CodPostal,Pais,Telefono,Fax,Web) VALUES('Forêts d''érables','Chantal Goulet','Accounting Manager','148 rue Chasseur','Ste-Hyacinthe','Québec','J2S 7S8','Canada','(514) 555-2955','(514) 555-2921',NULL)                                                                                                                ;

INSERT INTO Expedidores(Nombre,Telefono) VALUES('Speedy Express','(503) 555-9831');
INSERT INTO Expedidores(Nombre,Telefono) VALUES('United Package','(503) 555-3199');
INSERT INTO Expedidores(Nombre,Telefono) VALUES('Federal Shipping','(503) 555-9931');


INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Chai',1,1,'10 boxes x 20 bags',18);
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Chang',1,1,'24 - 12 oz bottles',19)									;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Aniseed Syrup',1,2,'12 - 550 ml bottles',10)                          ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Chef Anton''s Cajun Seasoning',2,2,'48 - 6 oz jars',22)               ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Chef Anton''s Gumbo Mix',2,2,'36 boxes',21.35)                        ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Grandma''s Boysenberry Spread',3,2,'12 - 8 oz jars',25)               ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Uncle Bob''s Organic Dried Pears',3,7,'12 - 1 lb pkgs.',30)           ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Northwoods Cranberry Sauce',3,2,'12 - 12 oz jars',40)                 ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Mishi Kobe Niku',4,6,'18 - 500 g pkgs.',97)                           ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Ikura',4,8,'12 - 200 ml jars',31)                                     ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Queso Cabrales',5,4,'1 kg pkg.',21)                                   ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Queso Manchego La Pastora',5,4,'10 - 500 g pkgs.',38)                 ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Konbu',6,8,'2 kg box',6)                                              ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Tofu',6,7,'40 - 100 g pkgs.',23.25)                                   ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Genen Shouyu',6,2,'24 - 250 ml bottles',15.5)                         ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Pavlova',7,3,'32 - 500 g boxes',17.45)                                ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Alice Mutton',7,6,'20 - 1 kg tins',39)                                ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Carnarvon Tigers',7,8,'16 kg pkg.',62.5)                              ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Teatime Chocolate Biscuits',8,3,'10 boxes x 12 pieces',9.2)           ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Sir Rodney''s Marmalade',8,3,'30 gift boxes',81)                      ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Sir Rodney''s Scones',8,3,'24 pkgs. x 4 pieces',10)                   ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Gustaf''s Knäckebröd',9,5,'24 - 500 g pkgs.',21)                      ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Tunnbröd',9,5,'12 - 250 g pkgs.',9)                                   ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Guaraná Fantástica',10,1,'12 - 355 ml cans',4.5)                      ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('NuNuCa Nuß-Nougat-Creme',11,3,'20 - 450 g glasses',14)                ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Gumbär Gummibärchen',11,3,'100 - 250 g bags',31.23)                   ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Schoggi Schokolade',11,3,'100 - 100 g pieces',43.9)                   ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Rössle Sauerkraut',12,7,'25 - 825 g cans',45.6)                       ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Thüringer Rostbratwurst',12,6,'50 bags x 30 sausgs.',123.79)          ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Nord-Ost Matjeshering',13,8,'10 - 200 g glasses',25.89)               ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Gorgonzola Telino',14,4,'12 - 100 g pkgs',12.5)                       ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Mascarpone Fabioli',14,4,'24 - 200 g pkgs.',32)                       ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Geitost',15,4,'500 g',2.5)                                            ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Sasquatch Ale',16,1,'24 - 12 oz bottles',14)                          ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Steeleye Stout',16,1,'24 - 12 oz bottles',18)                         ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Inlagd Sill',17,8,'24 - 250 g  jars',19)                              ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Gravad lax',17,8,'12 - 500 g pkgs.',26)                               ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Côte de Blaye',18,1,'12 - 75 cl bottles',263.5)                       ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Chartreuse verte',18,1,'750 cc per bottle',18)                        ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Boston Crab Meat',19,8,'24 - 4 oz tins',18.4)                         ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Jack''s New England Clam Chowder',19,8,'12 - 12 oz cans',9.65)        ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Singaporean Hokkien Fried Mee',20,5,'32 - 1 kg pkgs.',14)             ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Ipoh Coffee',20,1,'16 - 500 g tins',46)                               ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Gula Malacca',20,2,'20 - 2 kg bags',19.45)                            ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Rogede sild',21,8,'1k pkg.',9.5)                                      ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Spegesild',21,8,'4 - 450 g glasses',12)                               ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Zaanse koeken',22,3,'10 - 4 oz boxes',9.5)                            ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Chocolade',22,3,'10 pkgs.',12.75)                                     ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Maxilaku',23,3,'24 - 50 g pkgs.',20)                                  ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Valkoinen suklaa',23,3,'12 - 100 g bars',16.25)                       ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Manjimup Dried Apples',24,7,'50 - 300 g pkgs.',53)                    ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Filo Mix',24,5,'16 - 2 kg boxes',7)                                   ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Perth Pasties',24,6,'48 pieces',32.8)                                ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Tourtière',25,6,'16 pies',7.45)                                       ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Pâté chinois',25,6,'24 boxes x 2 pies',24)                            ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Gnocchi di nonna Alice',26,5,'24 - 250 g pkgs.',38)                   ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Ravioli Angelo',26,5,'24 - 250 g pkgs.',19.5)                         ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Escargots de Bourgogne',27,8,'24 pieces',13.25)                       ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Raclette Courdavault',28,4,'5 kg pkg.',55)                            ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Camembert Pierrot',28,4,'15 - 300 g rounds',34)                       ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Sirop d''érable',29,2,'24 - 500 ml bottles',28.5)                     ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Tarte au sucre',29,3,'48 pies',49.3)                                  ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Vegie-spread',7,2,'15 - 625 g jars',43.9)                             ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Wimmers gute Semmelknödel',12,5,'20 bags x 4 pieces',33.25)           ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Louisiana Fiery Hot Pepper Sauce',2,2,'32 - 8 oz bottles',21.05)      ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Louisiana Hot Spiced Okra',2,2,'24 - 8 oz jars',17)                   ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Laughing Lumberjack Lager',16,1,'24 - 12 oz bottles',14)              ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Scottish Longbreads',8,3,'10 boxes x 8 pieces',12.5)                  ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Gudbrandsdalsost',15,4,'10 kg pkg.',36)                               ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Outback Lager',7,1,'24 - 355 ml bottles',15)                          ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Flotemysost',15,4,'10 - 500 g pkgs.',21.5)                            ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Mozzarella di Giovanni',14,4,'24 - 200 g pkgs.',34.8)                 ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Röd Kaviar',17,8,'24 - 150 g jars',15)                                ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Longlife Tofu',4,7,'5 kg pkg.',10)                                    ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Rhönbräu Klosterbier',12,1,'24 - 0.5 l bottles',7.75)                 ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Lakkalikööri',23,1,'500 ml',18)                                       ;
INSERT INTO Productos(Nombre,ID_Proveedor,ID_Categoria,CantidadPorUnidad,PrecioUnitario) VALUES('Original Frankfurter grüne Soße',12,2,'12 boxes',13)                  ;



INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10248,'VINET',5,'1996/4/7','1996/4/12','1996/4/10',3,
	'Vins et alcools Chevalier','59 rue de l''Abbaye','Reims',
	NULL,'51100','France');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10249,'TOMSP',6,'1996/7/5','1996/8/16','1996/7/10',1,
	'Toms Spezialitäte','Luisenstr. 48','Münster',
	NULL,'44087','Germany');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10250,'HANAR',4,'1996/7/8','1996/8/5','1996/7/12',2,
	'Hanari Carnes','Rua do Paço, 67','Rio de Janeiro',
	'RJ','05454-876','Brazil');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10251,'VICTE',3,'1996/7/8/','1996/8/5','1996/7/15',1,
	'Victuailles en stock','2, rue du Commerce','Lyo',
	NULL,'69004','France');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10252,'SUPRD',4,'1996/9/7','1996/6/8','1996/11/7',2,
	'Suprêmes délices','Boulevard Tirou, 255','Charleroi',
	NULL,'B-6000','Belgium');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10253,'HANAR',3,'1996/7/10','1996/7/24','1996/7/16',2,
	'Hanari Carnes','Rua do Paço, 67','Rio de Janeiro',
	'RJ','05454-876','Brazil');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10254,'CHOPS',5,'1996/7/11','1996/8/8','1996/7/23',2,
	'Chop-suey Chinese','Hauptstr. 31','Ber',
	NULL,'3012','Switzerland');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10256,'WELLI',3,'1996/7/15','1996/8/12','1996/7/17',2,
	'Wellington Importadora','Rua do Mercado, 12','Resende',
	'SP','08737-363','Brazil');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10257,'HILAA',4,'1996/7/22','1996/8/13','1996/7/22',3,
	'HILARION-Abastos','Carrera 22 con Ave. Carlos Soublette #8-35','San Cristóbal',
	'Táchira','5022','Venezuela');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10258,'ERNSH',1,'1996/07/17','1996/08/14','1996/07/23',1,
	'Ernst Handel','Kirchgasse 6','Graz',
	NULL,'8010','Austria');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10259,'CENTC',4,'1996/07/18','1996/08/15','1996/07/25',3,
	'Centro comercial Moctezuma','Sierras de Granada 9993','México D.F.',
	NULL,'05022','Mexico');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10260,'OTTIK',4,'1996/07/19','1996/08/16','1996/07/29',1,
	'Ottilies Käselade','Mehrheimerstr. 369','Köl',
	NULL,'50739','Germany');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10261,'QUEDE',4,'1996/07/19','1996/08/01','1996/07/30',2,
	'Que Delícia','Rua da Panificadora, 12','Rio de Janeiro',
	'RJ','02389-673','Brazil');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10264,'FOLKO',6,'1996/07/24','1996/08/21','1996/08/23',3,
	'Folk och fä HB','Åkergatan 24','Bräcke',
	NULL,'S-844 67','Sweden');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10265,'BLONP',2,'1996/07/25','1996/08/22','1996/08/12',1,
	'Blondel père et fils','24, place Kléber','Strasbourg',
	NULL,'67000','France');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10266,'WARTH',3,'1996/07/07','1996/07/12','1996/07/10',3,
	'Wartian Herkku','Torikatu 38','Oulu',
	NULL,'90110','Finland');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10267,'FRANK',4,'1996/07/29','1996/08/23','1996/08/01',1,
	'Frankenversand','Berliner Platz 43','Münche',
	NULL,'80805','Germany');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10269,'WHITC',5,'1996/11/11','1996/11/16','1996/11/27',1,
	'White Clover Markets','1029 - 12th Ave. S.','Seattle',
	'WA','98124','USA');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10270,'WARTH',1,'1996/01/06','1996/01/09','1996/01/13',1,
	'Wartian Herkku','Torikatu 38','Oulu',
	NULL,'90110','Finland');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10271,'SPLIR',6,'1996/01/16','1996/01/19','1996/01/24',2,
	'Split Rail Beer & Ale','P.O. Box 555','Lander',
	'WY','82520','USA');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10272,'RATTC',6,'1996/02/08','1996/02/10','1996/02/15',2,
	'Rattlesnake Canyon Grocery','2817 Milton Dr.','Albuquerque',
	'NM','87110','USA');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10273,'QUICK',3,'1996/05/08','1996/05/10','1996/05/12',3,
	'QUICK-Stop','Taucherstraße 10','Cunewalde',
	NULL,'01307','Germany');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10274,'VINET',6,'1996/06/16','1996/06/21','1996/06/26',1,
	'Vins et alcools Chevalier','59 rue de l''Abbaye','Reims',
	NULL,'51100','France');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10275,'MAGAA',1,'1996/07/07','1996/07/11','1996/07/15',1,
	'Magazzini Alimentari Riuniti','Via Ludovico il Moro 22','Bergamo',
	NULL,'24100','Italy');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10276,'TORTU',8,'1996/08/05','1996/08/08','1996/08/12',3,
	'Tortuga Restaurante','Avda. Azteca 123','México D.F.',
	NULL,'05033','Mexico');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10277,'MORGK',2,'1996/09/01','1996/09/16','1996/09/19',3,
	'Morgenstern Gesundkost','Heerstr. 22','Leipzig',
	NULL,'04179','Germany');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10280,'BERGS',2,'1996/08/14','1996/08/16','1996/08/19',1,
	'Berglunds snabbköp','Berguvsvägen  8','Luleå',
	NULL,'S-958 22','Sweden');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10281,'ROMEY',4,'1996/04/14','1996/04/16','1996/04/21',1,
	'Romero y tomillo','Gran Vía, 1','Madrid',
	NULL,'28001','Spain');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10282,'ROMEY',4,'1996/05/16','1996/05/19','1996/05/21',1,
	'Romero y tomillo','Gran Vía, 1','Madrid',
	NULL,'28001','Spain');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10283,'LILAS',3,'1996/03/10','1996/03/11','1996/03/16',3,
	'LILA-Supermercado','Carrera 52 con Ave. Bolívar #65-98 Llano Largo','Barquisimeto',
	'Lara','3508','Venezuela');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10284,'LEHMS',4,'1996/10/12','1996/10/16','1996/10/20',1,
	'Lehmanns Marktstand','Magazinweg 7','Frankfurt a.M.',
	NULL,'60528','Germany');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10285,'QUICK',1,'1996/01/05','1996/01/12','1996/01/15',2,
	'QUICK-Stop','Taucherstraße 10','Cunewalde',
	NULL,'01307','Germany');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10288,'REGGC',4,'1996/08/23','1996/08/25','1996/08/31',1,
	'Reggiani Caseifici','Strada Provinciale 124','Reggio Emilia',
	NULL,'42100','Italy');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10289,'BSBEV',7,'1996/06/03','1996/06/09','1996/06/16',3,
	'B''s Beverages','Fauntleroy Circus','Londo',
	NULL,'EC2 5NT','UK');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10291,'QUEDE',6,'1996/05/12','1996/05/15','1996/05/19',2,
	'Que Delícia','Rua da Panificadora, 12','Rio de Janeiro',
	'RJ','02389-673','Brazil');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10292,'TRADH',1,'1996/12/21','1996/12/24','1996/12/25',2,
	'Tradiçao Hipermercados','Av. Inês de Castro, 414','Sao Paulo',
	'SP','05634-030','Brazil');
INSERT INTO Pedidos(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10293,'TORTU',1,'1996/09/01','1996/09/05','1996/09/30',3,
	'Tortuga Restaurante','Avda. Azteca 123','México D.F.',
	NULL,'05033','Mexico');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10294,'RATTC',4,'1996/09/12','1996/09/15','1996/09/21',2,
	'Rattlesnake Canyon Grocery','2817 Milton Dr.','Albuquerque',
	'NM','87110','USA');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10295,'VINET',2,'1996/02/07','1996/02/09','1996/02/14',2,
	'Vins et alcools Chevalier','59 rue de l''Abbaye','Reims',
	NULL,'51100','France');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10296,'LILAS',6,'1996/03/15','1996/03/30','1996/04/02',1,
	'LILA-Supermercado','Carrera 52 con Ave. Bolívar #65-98 Llano Largo','Barquisimeto',
	'Lara','3508','Venezuela');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10297,'BLONP',5,'1996/04/12','1996/04/16','1996/04/14',2,
	'Blondel père et fils','24, place Kléber','Strasbourg',
	NULL,'67000','France');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10298,'HUNGO',6,'1996/05/22','1996/05/30','1996/05/26',2,
	'Hungry Owl All-Night Grocers','8 Johnstown Road','Cork',
	'Co. Cork',NULL,'Ireland');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10299,'RICAR',4,'1996/04/01','1996/04/12','1996/04/05',2,
	'Ricardo Adocicados','Av. Copacabana, 267','Rio de Janeiro',
	'RJ','02389-890','Brazil');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10300,'MAGAA',2,'1996/09/25','1996/10/07','1996/10/4',2,
	'Magazzini Alimentari Riuniti','Via Ludovico il Moro 22','Bergamo',
	NULL,'24100','Italy');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10302,'SUPRD',4,'1996/08/10','1996/08/12','1996/08/11',2,
	'Suprêmes délices','Boulevard Tirou, 255','Charleroi',
	NULL,'B-6000','Belgium');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10303,'GODOS',7,'1996/09/05','1996/09/10','1996/09/18',2,
	'Godos Cocina Típica','C/ Romero, 33','Sevilla',
	NULL,'41101','Spain');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10304,'TORTU',1,'1996/10/10','1996/10/12','1996/10/12',2,
	'Tortuga Restaurante','Avda. Azteca 123','México D.F.',
	NULL,'05033','Mexico');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10306,'ROMEY',1,'1996/10/12','1996/10/14','1996/10/23',3,
	'Romero y tomillo','Gran Vía, 1','Madrid',
	NULL,'28001','Spain');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10307,'LONEP',2,'1996/07/17','1996/08/05','1996/08/02',2,
	'Lonesome Pine Restaurant','89 Chiaroscuro Rd.','Portland',
	'OR','97219','USA');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10308,'ANATR',7,'1996/10/18','1996/10/22','1996/10/24',3,
	'Ana Trujillo Emparedados y helados','Avda. de la Constitución 2222','México D.F.',
	NULL,'05021','Mexico');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10309,'HUNGO',3,'1996/09/19','1996/10/17','1996/10/23',1,
	'Hungry Owl All-Night Grocers','8 Johnstown Road','Cork',
	'Co. Cork',NULL,'Ireland');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10311,'RATTC',1,'1996/09/20','1996/10/04','1996/09/26',3,
	'Du monde entier','67, rue des Cinquante Otages','Nantes',
	NULL,'44000','France');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10312,'WANDK',2,'1996/09/23','1996/10/21','1996/10/10',2,
	'Die Wandernde Kuh','Adenauerallee 900','Stuttgart',
	NULL,'70563','Germany');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10313,'QUICK',2,'1996/09/24','1996/10/22','1996/10/4',2,
	'QUICK-Stop','Taucherstraße 10','Cunewalde',
	NULL,'01307','Germany');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10314,'RATTC',1,'1996/09/25','1996/10/23','1996/10/4',2,
	'Rattlesnake Canyon Grocery','2817 Milton Dr.','Albuquerque',
	'NM','87110','USA');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10315,'ISLAT',4,'1996/09/26','1996/09/28','1996/09/30',2,
	'Island Trading','Garden House Crowther Way','Cowes',
	'Isle of Wight','PO31 7PJ','UK');
INSERT INTO Pedidos
(ID_Pedido,ID_Cliente,ID_Empleado,FechaPedido,FechaRequerida,
	FechaExpedicion,FormaExpedicion,NombreExpedicion,DireccionExpedicion,
	CiudadExpedicion,RegionExpedicion,CodPostalExpedicion,PaisExpedicion)
VALUES (10316,'RATTC',1,'1996/06/27','1996/06/30','1996/06/29',3,
	'Rattlesnake Canyon Grocery','2817 Milton Dr.','Albuquerque',
	'NM','87110','USA');


INSERT INTO DetallePedido VALUES(10248,42,9.8,10,0)             ;
INSERT INTO DetallePedido VALUES(10248,11,14,12,0)				;
INSERT INTO DetallePedido VALUES(10248,72,34.8,5,0)             ;
INSERT INTO DetallePedido VALUES(10249,14,18.6,9,0)             ;
INSERT INTO DetallePedido VALUES(10249,51,42.4,40,0)            ;
INSERT INTO DetallePedido VALUES(10250,41,7.7,10,0)             ;
INSERT INTO DetallePedido VALUES(10250,51,42.4,35,0.15)         ;
INSERT INTO DetallePedido VALUES(10250,65,16.8,15,0.15)         ;
INSERT INTO DetallePedido VALUES(10251,22,16.8,6,0.05)          ;
INSERT INTO DetallePedido VALUES(10251,57,15.6,15,0.05)         ;
INSERT INTO DetallePedido VALUES(10251,65,16.8,20,0)            ;
INSERT INTO DetallePedido VALUES(10252,20,64.8,40,0.05)         ;
INSERT INTO DetallePedido VALUES(10252,33,2,25,0.05)            ;
INSERT INTO DetallePedido VALUES(10252,60,27.2,40,0)            ;
INSERT INTO DetallePedido VALUES(10253,31,10,20,0)              ;
INSERT INTO DetallePedido VALUES(10253,39,14.4,42,0)            ;
INSERT INTO DetallePedido VALUES(10253,49,16,40,0)              ;
INSERT INTO DetallePedido VALUES(10254,24,3.6,15,0.15)          ;
INSERT INTO DetallePedido VALUES(10254,55,19.2,21,0.15)         ;
INSERT INTO DetallePedido VALUES(10254,74,8,21,0)               ;
INSERT INTO DetallePedido VALUES(10256,53,26.2,15,0)            ;
INSERT INTO DetallePedido VALUES(10256,77,10.4,12,0)            ;
INSERT INTO DetallePedido VALUES(10257,27,35.1,25,0)            ;
INSERT INTO DetallePedido VALUES(10257,39,14.4,6,0)             ;
INSERT INTO DetallePedido VALUES(10257,77,10.4,15,0)            ;
INSERT INTO DetallePedido VALUES(10258,2,15.2,50,0.2)           ;
INSERT INTO DetallePedido VALUES(10258,5,17,65,0.2)             ;
INSERT INTO DetallePedido VALUES(10258,32,25.6,6,0.2)           ;
INSERT INTO DetallePedido VALUES(10259,21,8,10,0)               ;
INSERT INTO DetallePedido VALUES(10259,37,20.8,1,0)             ;
INSERT INTO DetallePedido VALUES(10260,41,7.7,16,0.25)          ;
INSERT INTO DetallePedido VALUES(10260,57,15.6,50,0)            ;
INSERT INTO DetallePedido VALUES(10260,62,39.4,15,0.25)         ;
INSERT INTO DetallePedido VALUES(10260,70,12,21,0.25)           ;
INSERT INTO DetallePedido VALUES(10261,21,8,20,0)               ;
INSERT INTO DetallePedido VALUES(10261,35,14.4,20,0)            ;
INSERT INTO DetallePedido VALUES(10264,2,15.2,35,0)             ;
INSERT INTO DetallePedido VALUES(10264,41,7.7,25,0.15)          ;
INSERT INTO DetallePedido VALUES(10265,17,31.2,30,0)            ;
INSERT INTO DetallePedido VALUES(10265,70,12,20,0)              ;
INSERT INTO DetallePedido VALUES(10266,12,30.4,12,0.05)         ;
INSERT INTO DetallePedido VALUES(10267,40,14.7,50,0)            ;
INSERT INTO DetallePedido VALUES(10267,59,44,70,0.15)           ;
INSERT INTO DetallePedido VALUES(10267,76,14.4,15,0.15)         ;
INSERT INTO DetallePedido VALUES(10269,33,2,60,0.05)            ;
INSERT INTO DetallePedido VALUES(10269,72,27.8,20,0.05)         ;
INSERT INTO DetallePedido VALUES(10270,36,15.2,30,0)            ;
INSERT INTO DetallePedido VALUES(10270,43,36.8,25,0)            ;
INSERT INTO DetallePedido VALUES(10271,33,2,24,0)               ;
INSERT INTO DetallePedido VALUES(10272,20,64.8,6,0)             ;
INSERT INTO DetallePedido VALUES(10272,31,10,40,0)              ;
INSERT INTO DetallePedido VALUES(10272,72,27.8,24,0)            ;
INSERT INTO DetallePedido VALUES(10273,10,24.8,24,0.05)         ;
INSERT INTO DetallePedido VALUES(10273,31,10,15,0.05)           ;
INSERT INTO DetallePedido VALUES(10273,33,2,20,0)               ;
INSERT INTO DetallePedido VALUES(10273,40,14.7,60,0.05)         ;
INSERT INTO DetallePedido VALUES(10273,76,14.4,33,0.05)         ;
INSERT INTO DetallePedido VALUES(10274,71,17.2,20,0)            ;
INSERT INTO DetallePedido VALUES(10274,72,27.8,7,0)             ;
INSERT INTO DetallePedido VALUES(10275,24,3.6,12,0.05)          ;
INSERT INTO DetallePedido VALUES(10275,59,44,6,0.05)            ;
INSERT INTO DetallePedido VALUES(10276,10,24.8,15,0)            ;
INSERT INTO DetallePedido VALUES(10276,13,4.8,10,0)             ;
INSERT INTO DetallePedido VALUES(10277,28,36.4,20,0)            ;
INSERT INTO DetallePedido VALUES(10277,62,39.4,12,0)            ;
INSERT INTO DetallePedido VALUES(10280,24,3.6,12,0)             ;
INSERT INTO DetallePedido VALUES(10280,55,19.2,20,0)            ;
INSERT INTO DetallePedido VALUES(10280,75,6.2,30,0)             ;
INSERT INTO DetallePedido VALUES(10281,19,7.3,1,0)              ;
INSERT INTO DetallePedido VALUES(10281,24,3.6,6,0)              ;
INSERT INTO DetallePedido VALUES(10281,35,14.4,4,0)             ;
INSERT INTO DetallePedido VALUES(10282,30,20.7,6,0)             ;
INSERT INTO DetallePedido VALUES(10282,57,15.6,2,0)             ;
INSERT INTO DetallePedido VALUES(10283,15,12.4,20,0)            ;
INSERT INTO DetallePedido VALUES(10283,19,7.3,18,0)             ;
INSERT INTO DetallePedido VALUES(10283,60,27.2,35,0)            ;
INSERT INTO DetallePedido VALUES(10283,72,27.8,3,0)             ;
INSERT INTO DetallePedido VALUES(10284,27,35.1,15,0.25)         ;
INSERT INTO DetallePedido VALUES(10284,44,15.5,21,0)            ;
INSERT INTO DetallePedido VALUES(10284,60,27.2,20,0.25)         ;
INSERT INTO DetallePedido VALUES(10284,67,11.2,5,0.25)          ;
INSERT INTO DetallePedido VALUES(10285,1,14.4,45,0.2)           ;
INSERT INTO DetallePedido VALUES(10285,40,14.7,40,0.2)          ;
INSERT INTO DetallePedido VALUES(10285,53,26.2,36,0.2)          ;
INSERT INTO DetallePedido VALUES(10289,3,8,30,0)                ;
INSERT INTO DetallePedido VALUES(10289,64,26.6,9,0)             ;
INSERT INTO DetallePedido VALUES(10291,13,4.8,20,0.1)           ;
INSERT INTO DetallePedido VALUES(10291,44,15.5,24,0.1)          ;
INSERT INTO DetallePedido VALUES(10291,51,42.4,2,0.1)           ;
INSERT INTO DetallePedido VALUES(10292,20,64.8,20,0)            ;
INSERT INTO DetallePedido VALUES(10293,18,50,12,0)              ;
INSERT INTO DetallePedido VALUES(10293,24,3.6,10,0)             ;
INSERT INTO DetallePedido VALUES(10293,63,35.1,5,0)             ;
INSERT INTO DetallePedido VALUES(10293,75,6.2,6,0)              ;
INSERT INTO DetallePedido VALUES(10294,1,14.4,18,0)             ;
INSERT INTO DetallePedido VALUES(10294,17,31.2,15,0)            ;
INSERT INTO DetallePedido VALUES(10294,43,36.8,15,0)            ;
INSERT INTO DetallePedido VALUES(10294,60,27.2,21,0)            ;
INSERT INTO DetallePedido VALUES(10294,75,6.2,6,0)              ;
INSERT INTO DetallePedido VALUES(10295,56,30.4,4,0)             ;
INSERT INTO DetallePedido VALUES(10296,11,16.8,12,0)            ;
INSERT INTO DetallePedido VALUES(10296,16,13.9,30,0)            ;
INSERT INTO DetallePedido VALUES(10296,69,28.8,15,0)            ;
INSERT INTO DetallePedido VALUES(10297,39,14.4,60,0)            ;
INSERT INTO DetallePedido VALUES(10297,72,27.8,20,0)            ;
INSERT INTO DetallePedido VALUES(10298,2,15.2,40,0)             ;
INSERT INTO DetallePedido VALUES(10298,36,15.2,40,0.25)         ;
INSERT INTO DetallePedido VALUES(10298,59,44,30,0.25)           ;
INSERT INTO DetallePedido VALUES(10298,62,39.4,15,0)            ;
INSERT INTO DetallePedido VALUES(10299,19,7.3,15,0)             ;
INSERT INTO DetallePedido VALUES(10299,70,12,20,0)              ;
INSERT INTO DetallePedido VALUES(10300,66,13.6,30,0)            ;
INSERT INTO DetallePedido VALUES(10300,68,10,20,0)              ;
INSERT INTO DetallePedido VALUES(10302,17,31.2,40,0)            ;
INSERT INTO DetallePedido VALUES(10302,28,36.4,28,0)            ;
INSERT INTO DetallePedido VALUES(10302,43,36.8,12,0)            ;
INSERT INTO DetallePedido VALUES(10303,40,14.7,40,0.1)          ;
INSERT INTO DetallePedido VALUES(10303,65,16.8,30,0.1)          ;
INSERT INTO DetallePedido VALUES(10303,68,10,15,0.1)            ;
INSERT INTO DetallePedido VALUES(10304,49,16,30,0)              ;
INSERT INTO DetallePedido VALUES(10304,59,44,10,0)              ;
INSERT INTO DetallePedido VALUES(10304,71,17.2,2,0)             ;
INSERT INTO DetallePedido VALUES(10306,30,20.7,10,0)            ;
INSERT INTO DetallePedido VALUES(10306,53,26.2,10,0)            ;
INSERT INTO DetallePedido VALUES(10306,54,5.9,5,0)              ;
INSERT INTO DetallePedido VALUES(10307,62,39.4,10,0)            ;
INSERT INTO DetallePedido VALUES(10307,68,10,3,0)               ;
INSERT INTO DetallePedido VALUES(10308,69,28.8,1,0)             ;
INSERT INTO DetallePedido VALUES(10308,70,12,5,0)               ;
INSERT INTO DetallePedido VALUES(10309,4,17.6,20,0)             ;
INSERT INTO DetallePedido VALUES(10309,6,20,30,0)               ;
INSERT INTO DetallePedido VALUES(10309,42,11.2,2,0)             ;
INSERT INTO DetallePedido VALUES(10309,43,36.8,20,0)            ;
INSERT INTO DetallePedido VALUES(10309,71,17.2,3,0)             ;
INSERT INTO DetallePedido VALUES(10311,42,11.2,6,0)             ;
INSERT INTO DetallePedido VALUES(10311,69,28.8,7,0)             ;
INSERT INTO DetallePedido VALUES(10312,28,36.4,4,0)             ;
INSERT INTO DetallePedido VALUES(10312,43,36.8,24,0)            ;
INSERT INTO DetallePedido VALUES(10312,53,26.2,20,0)            ;
INSERT INTO DetallePedido VALUES(10312,75,6.2,10,0)             ;
INSERT INTO DetallePedido VALUES(10313,36,15.2,12,0)            ;
INSERT INTO DetallePedido VALUES(10314,32,25.6,40,0.1)          ;
INSERT INTO DetallePedido VALUES(10314,58,10.6,30,0.1)          ;
INSERT INTO DetallePedido VALUES(10314,62,39.4,25,0.1)          ;
INSERT INTO DetallePedido VALUES(10315,34,11.2,14,0)            ;
INSERT INTO DetallePedido VALUES(10315,70,12,30,0)              ;
INSERT INTO DetallePedido VALUES(10316,41,7.7,10,0)             ;
INSERT INTO DetallePedido VALUES(10316,62,39.4,70,0)            ;

