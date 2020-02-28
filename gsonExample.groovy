import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

String myURL = "https://gist.github.com/aa464b39c1208c357b8d4ae7fe210bbb.git"
String filename = "data.json"
//GSON parts
Type TT_mapStringString = new TypeToken<HashMap<String, HashMap<String, Object>>>() {}.getType();
Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
 
// load daata from a Git json
HashMap<String, HashMap<String, Object>> database = ScriptingEngine.gitScriptRun(
											myURL,
											filename,null)
//Alternate method to load from a string
//File	cachejson = ScriptingEngine.fileFromGit(myURL, filename)
//String jsonString = null;
//InputStream inPut = null;
//inPut = FileUtils.openInputStream(cachejson);
//jsonString = IOUtils.toString(inPut);											
//HashMap<String, HashMap<String, Object>> database = gson.fromJson(StringVersionOfData, TT_mapStringString);
// Reading Data
for(String key:database.keySet()) {
	HashMap<String, Object> data = database.get(key)											
	println "Loading Data from set: "+key
	
	for(String keyLevel2:data.keySet()){
		println "\tContent at: "+keyLevel2+" is "+data.get(keyLevel2)
	}
}

// Writing data
HashMap<String, Object> newData = new HashMap<>()
newData.put("myNewKey","MyValue")
newData.put("myOtherKey",37.42)
database.put("NewDataset",newData)

 
String writeOut = gson.toJson(database, TT_mapStringString);
println "New database JSON content = \n\n"+writeOut

ScriptingEngine.pushCodeToGit(myURL, "master", filename, writeOut, "saving CSG database", false);
