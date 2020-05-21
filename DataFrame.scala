DF
import spark.implicits._
 val spark = SparkSession
      .builder
      .appName("SparkSQL")
      .master("local[*]")
     // .config("spark.sql.warehouse.dir", "file:///C:/temp") // Necessary to work around a Windows bug in Spark 2.0.0; omit if you're not on Windows.
      .getOrCreate()

------------------DataFrames and Datasets-----------------------
	Lo que diferencia a un dataframe de un dataset es que un dataframe es un dataset que a la vez está organizado en columnas, 
	de modo que en el dataframe tendremos los datos estructurados y cada columna con su nombre correspondiente. 
	
	DataFrames represent structured data in a tabular form
		─ DataFrames model data similar to tables in an RDBMS
		
	Datasets represent data as a collection of objects of a specified type
		─ Datasets are strongly-typed—type checking is enforced at compile time rather than run time
		- DataFrame is an alias for Dataset[Row]
---------------------------------------------------------------
-----------------------Creating a DataFrame--------------------
		
		val usersDF = spark.read.json("users.json","myfile2.json")
		val accountsDF = spark.read.table("accounts")
		val a = spark.read.csv("/loudacre/myFile.csv")
		val b = spark.read.option("inferSchema","true").csv("people.csv").printSchema() intenta sacar el tipo de los datos
		val c = spark.read.option("inferSchema","true").option("header","true").csv("people.csv") incluye la cabecera
		
		//created manually
		val mydata = List(("Josiah","Bartlett"),("Harry","Potter"))
		val myDF = spark.createDataFrame(mydata)
		//or
		val someDF = Seq(
 		 (8, "bat"),
 		 (64, "mouse")
		).toDF("number", "word")
		
		usersDF.printSchema shows the schema inferred
		usersDF.show
		usersDF.count
		usersDF.first
		usersDF.take(n): returns the first n rows as an array
		usersDF.show(n): display the first n rows in tabular form
		usersDF.collect: returns all the rows in the DataFrame as an array
		usersDF.write: save the data to a file or other data source
		nameAgeDF = usersDF.select("name","age")
		over20DF = usersDF.where("age > 20")
		
		DEFINING SCHEMA EXPLICITILY
		import org.apache.spark.sql.types._
		val columnsList = List(
			StructField("pcode", StringType),
			StructField("lastName", StringType),
			StructField("firstName", StringType),
			StructField("age", IntegerType))
			
		val peopleSchema = StructType(columnsList)
		spark.read.option("header","true").schema(peopleSchema).csv("D:/eclipse_scala/workspace/moduley/devices.json").printSchema()

---------------------------------------------------------------
-----------------------Using a DataFrame-----------------------
to make some operations in the selected columns we have to select them like this:
- peopleDF.select($"lastName", $"age" * 10).show
or
- peopleDF.where(peopleDF("firstName").startsWith("A")).show
peopleDF.select($"lastName",($"age" * 10).alias("age_10")).show //using alias
 

examples:
peopleDF.groupBy("pcode").count().show()
val stateAccountsDF = accountsDF.where(accountsDF("state") === stateCode) --> 3 iguales y el nombre entre parentesis
val peopleDF = spark.read.option("header","true").csv("people-no-pcode.csv")
val pcodesDF = spark.read.option("header","true").csv("pcodes.csv")
peopleDF.join(pcodesDF, "pcode").show()
peopleDF.join(pcodesDF, "pcode", "left_outer").show() //fijarse como hace el left outer join
peopleDF.join(pcodesDF,peopleDF("pcode") === pcodesDF("pcode"),"left_outer").show //para seleccionar el campo por el que hace join o
peopleDF.join(zcodesDF, $"pcode" === $"zip").show o
peopleDF.join(zcodesDF, peopleDF.pcode == zcodesDF.zip).show()
orderDevicesDF = sumDevicesDF.orderBy($"active_num".desc)
//cast
DF.withColumn("new_name_col",col("current_name_col").cast("Integer"))
//Cast inside struct (Struct name -> originalCost, column to change-> amount
val file=spark.read.parquet("hdfs://nameservice1/src/kafka/promotion-platform-search-log-event/source=LHR/hash=651451cd4b7b03c44d5985a61e7f6f3d/date=2020-04-26/part-00017-a7d8bd52-436b-43fe-a26e-20c127a84ba6.c000.snappy.parquet")
                     
                        val columns = file
                          .select(col(s"originalCost.*"))
                          .columns
                          .filterNot(Array("amount").contains) //select only the columns we want to change
                          .map(name => col(s"originalCost.$name"))
    val file2 =     file.withColumn("originalCost", struct(columns ++
                          castNestedColumnIfExists(file, "originalCost.amount", DoubleType) : _*))
//alias groupby
val totalValues=retention.groupBy("database_name","table_name").agg(sum("num_rows_in_partition").alias("Total")).show

to work with columns
accountsDF.select($"first_name")
val fnCol = accountsDF("first_name")
val lucyCol = (fnCol === "Lucy")
accountsDF.select($"first_name",$"last_name",lucyCol).show



-------------------------------------------------------------
-----------------------Saving a DataFrame--------------------
		
DataFrameWriter methods
─ format specifies a data source type
─ mode determines the behavior if the directory or table already exists
	─ error, overwrite, append, or ignore (default is error)
─ partitionBy stores data in partitioned directories in the form column=value (as with Hive/Impala partitioning)
─ option specifies properties for the target data source
─ save saves the data as files in the specified directory Or use json, csv, parquet, and so on
─ saveAsTable saves the data to a Hive metastore table
─ Uses default table location (/user/hive/warehouse)
─ Set path option to override location

myDF.write.save("mydata")
myDF.write.mode("append").option("path","/loudacre/mydata").saveAsTable("my_table")
filterDF.write.mode("overwrite").parquet("/loudacre/accounts_by_state/"+state-code)
myDF.write.json("mydata")
myDF.write.csv("/loudacre/accounts_zip94913")
devDF.write.parquet("/loudacre/devices_parquet")
nameDevDF.write.option("path","/loudacre/name_dev").saveAsTable("name_dev")
-------------------------------------------------------------
------------------------PERSISTENCE--------------------------
	
- MEMORY_ONLY: Store data in memory if it fits
─ DISK_ONLY: Store all partitions on disk
─ MEMORY_AND_DISK: Store any partition that does not fit in memory on disk	

con cache() se usa el storage_level por defecto, (memory_only). con persist, puedes especificar el level storage

Tables and views can be persisted in memory using CACHE TABLE-> spark.sql("CACHE TABLE people")
CACHE TABLE can create a view based on a SQL query and cache it at the same time -> spark.sql("CACHE TABLE over_20 AS SELECT * FROM people WHERE age > 20")

import org.apache.spark.storage.StorageLevel
myDF.persist(StorageLevel.DISK_ONLY)
myDF.cache()
myDF.unpersist()
myDF.persist(new-level)


---COMPILACION Y EJECUCION---
cuando se hace el script por fichero.
mvn package en la carpeta donde esta el src y pom-xml

para ejecutar:
spark2-submit \
--class stubs.AccountsByState \  --> el nombre de la clase
target/accounts-by-state-1.0.jar CA --> el nombre del jar que crea cuando compila bien



