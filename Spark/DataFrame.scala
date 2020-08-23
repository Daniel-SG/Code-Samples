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
		//or DEFINING SCHEMA EXPLICITILY

		val data = Seq(
  			Row(8, "bat"),
 			Row(64, "mouse"),
 			Row(-27, "horse")
		)

	val schema = StructType(
  		List(
    			StructField("number", IntegerType, true),
    			StructField("word", StringType, true)
  			)
		)

	val df1 = spark.createDataFrame(
  		spark.sparkContext.parallelize(data),
  		schema
		)
//Creating manually complex data types Array and Struct

val data2 = Seq(
	//we create a row inside another row to make the struct
  Row(8,Array("1","2"),Row("James ","","Smith")), //firstname,middlename,secondname
  Row(64,Array("3","4"),Row("James ","","Smith")),
  Row(-27,Array("5","6"),Row("James ","","Smith"))
)

val namesDefinition = new StructType(
   Array(
      StructField("firstname",StringType, true),
      StructField("middlename",StringType, true),
      StructField("secondname",StringType, true)
      ))
      
val schema2 = StructType(
  List(
    StructField("number", IntegerType, true),
    StructField("arraySample", ArrayType(StringType), true), //it is an ArrayType
    StructField("name", StructType(namesDefinition), true) // it is StructType that we have defined above
  )
)

val df2 = spark.createDataFrame(
  spark.sparkContext.parallelize(data2),
  schema2
)
+------+-----------+-----------------+
|number|arraySample|             name|
+------+-----------+-----------------+
|     8|     [1, 2]|[James , , Smith]|
|    64|     [3, 4]|[James , , Smith]|
|   -27|     [5, 6]|[James , , Smith]|
+------+-----------+-----------------+

root
 |-- number: integer (nullable = true)
 |-- arraySample: array (nullable = true)
 |    |-- element: string (containsNull = true)
 |-- name: struct (nullable = true)
 |    |-- firstname: string (nullable = true)
 |    |-- middlename: string (nullable = true)
 |    |-- secondname: string (nullable = true)

//Creating manually array of Structs

val data3 = Seq(
  Row(8,Array(Row("Julius ","Cesar"),Row("Adolfo ","Dominguez"))),
  Row(64,Array(Row("Will ","Smith"),Row("Lionel ","Messi")))
 )


 namesDefinition = new StructType(
   Array(
      StructField("firstname",StringType, true),
      StructField("secondname",StringType, true)
      ))
      
     val schema3 = StructType(
  List(
    StructField("number", IntegerType, true),
    StructField("Complete_Name", ArrayType(StructType(namesDefinition)), true)
  )
)
val df3 = spark.createDataFrame(
  spark.sparkContext.parallelize(data3),
  schema3
) 

+------+----------------------------------------+
|number|Complete_Name                           |
+------+----------------------------------------+
|8     |[[Julius , Cesar], [Adolfo , Dominguez]]|
|64    |[[Will , Smith], [Lionel , Messi]]      |
+------+----------------------------------------+

root
 |-- number: integer (nullable = true)
 |-- Complete_Name: array (nullable = true)
 |    |-- element: struct (containsNull = true)
 |    |    |-- firstname: string (nullable = true)
 |    |    |-- secondname: string (nullable = true)

//------- Struct of arrays

// ---------------- Structs of arrays

val data4 = Seq(
  Row(8,Row(Array("a","b","c"),Array("1","2")))
 )
 
val  otro = new StructType(
   Array(
      StructField("characters",ArrayType(StringType), true),
      StructField("numbers",ArrayType(StringType), true)
      )
)

val schema4 = StructType(
  List(
    StructField("number", IntegerType, true),
    StructField("structArrays", StructType(otro), true)
  )
)
 val df4 = spark.createDataFrame(
  spark.sparkContext.parallelize(data4),
  schema4
) 

+------+-------------------+
|number|Another            |
+------+-------------------+
|8     |[[a, b, c], [1, 2]]|
+------+-------------------+

root
 |-- number: integer (nullable = true)
 |-- Another: struct (nullable = true)
 |    |-- characters: array (nullable = true)
 |    |    |-- element: string (containsNull = true)
 |    |-- numbers: array (nullable = true)
 |    |    |-- element: string (containsNull = true)
//we can define the schema explicitilly and load the data from an external source
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
---------------------Case class example ---------------------
object caseClassExample {
	def main(args: Array[String]){
		    val alice = Person("Alice", 25)
				val rabbit = Person("Bugs",  50)
				val duck = Person("Donald",  21)
				
				for(gente <- List(alice,rabbit,duck)){
				  gente match {
				    case Person("Alice",25) => println("Hello Alice")
				    case Person("Bugs",50) => println("Hello Bugs")
				    case Person("Donald",21) => println("Hello Donald")
				  }
				}
	}

}
case class Person(fName:String, age:Int) 

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

----------------Starting spark shell------------------
Spark shell has a number of different start-up options, including
─ master: specify the cluster to connect to
─ jars: Additional JAR files (Scala only)
─ py-files: Additional Python files (Python only)
─ name: the name the Spark Application UI uses for this application
─ Defaults to PySparkShell (Python) or Spark shell (Scala)
─ help: Show all the available shell options

The possible values for the master option include
─ yarn
─ spark://masternode:port (Spark Standalone)
─ mesos://masternode:port (Mesos)
─ local[*] runs locally with as many threads as cores (default)
─ local[n] runs locally with n threads
─ local runs locally with a single thread

spark2-shell --master yarn
---------------------------------------------------------------
-------------LOG LEVEL-----------------------------
Available log levels are
─ TRACE
─ DEBUG
─ INFO (default level in Spark applications)
─ WARN (default level in Spark shell)
─ ERROR
─ FATAL
─ OFF
spark.sparkContext.setLogLevel("INFO")
---------------------------------------------------------------
----------------------Run scala program------------------------
spark2-submit --class NameList MyJarFile.jar people.json namelist

General submit flags include
─ master: local, yarn, or a Mesos or Spark Standalone cluster manager
URI
─ jars: Additional JAR files (Scala and Java only)
─ pyfiles: Additional Python files (Python only)
─ driver-java-options: Parameters to pass to the driver JVM
▪ YARN-specific flags include
─ num-executors: Number of executors to start application with
─ driver-cores: Number cores to allocate for the Spark driver
─ queue: YARN queue to run in
▪ Show all available options
─ help
-----------------------------------------------------------------------
---------------------------Config properties---------------------------
spark.master: Cluster type or URI to submit application to
─ spark.app.name: Application name displayed in the Spark UI
─ spark.submit.deployMode: Whether to run application in client or
cluster mode (default: client)
─ spark.ui.port: Port to run the Spark Application UI (default 4040)
─ spark.executor.memory: How much memory to allocate to each
Executor (default 1g)
─ spark.pyspark.python: Which Python executable to use for Pyspark
----------------------------------------------------------------------
---------------------------PARTITIONS---------------------------------
leer particiones rdd -> myRDD.getNumPartitions()
Specify the number of partitions when data is read -> myRDD = sc.textFile(myfile,5)
repartition shuffles the data into more or fewer partitions -> newRDD = myRDD.repartition(15)
Specify the number of partitions created by transformations->countRDD = wordsRDD.reduceByKey(lambda v1, v2: v1 + v2, 15)
Viewing RDD Execution Plans -> Use the RDD toDebugString function


