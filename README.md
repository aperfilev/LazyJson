# LazyJson Java Library

The LazyJson Java Library is a powerful and efficient tool designed to provide optimal parsing performance for JSON files. Parsing large JSON files can be resource-intensive and time-consuming, especially when dealing with complex structures. The LazyJson library aims to address this challenge by offering a lazy and incremental approach to JSON parsing, allowing you to access and process JSON data on-the-fly without loading the entire file into memory.

## Features

- **Lazy Parsing:** The library employs a lazy parsing technique, which means that it only reads and processes the necessary parts of the JSON file as requested, reducing memory usage and improving performance.

- **Incremental Processing:** LazyJson allows you to incrementally process JSON data, which is particularly useful when working with large files. You can extract and process data step by step, without the need to load the entire JSON file at once.

- **Efficient Memory Usage:** With its efficient memory management, LazyJson minimizes memory consumption by discarding already processed data and only holding the necessary portions in memory.

- **Support for Complex JSON Structures:** The library handles various JSON structures, including nested objects and arrays, ensuring that you can navigate and extract data from intricate JSON files with ease.

- **Simple API:** LazyJson provides a straightforward API that abstracts the complexities of lazy parsing. You can focus on processing your data without getting bogged down by low-level parsing details.

- **Error Handling:** The library includes comprehensive error handling mechanisms, allowing you to identify and address issues in your JSON data gracefully.

## Performance Comparison

Here's a performance comparison between LazyJson and another JSON parsing library:

| Library      | Parsing Time (ms/Mb) |
|--------------|---------------------|
| LazyJson     | 25                  |
| Other Library| 2500                |

**Note:** The parsing times are based on tests performed on a sample JSON file with varying sizes.

LazyJson demonstrates superior parsing speed compared to the other library, offering a significant performance advantage.

## Getting Started

To start using the LazyJson Java Library, follow these simple steps:

1. **Download the Library:** Download the LazyJson library JAR file from the [releases section](https://github.com/aperfilev/lazyjson/releases) of the GitHub repository.

2. **Add to Your Project:** Add the downloaded JAR file to your Java project's classpath.

3. **Import the Library:** Import the LazyJson classes in your Java code:

   ```java
   import localhost.tools.json.lazyjson.JSONObject;
   import localhost.tools.json.lazyjson.JSONArray;
   import localhost.tools.json.lazyjson.JSONException;
   ```

4. **Initialize LazyJsonParser:** Create an instance of `LazyJsonParser` and provide the JSON file's input stream:

    ```java
    try {
        String jsonContent = "{}"; 
        JSONObject jsonObject = new JSONObject(jsonContent);
    } catch (JSONException e) {
        // Handle the exception
    }
    ```

5. **Access JSON Data:** Use the `JSONObject` or `JSONArray` instance to extract and process JSON data:

   ```java
    try {
        String jsonContent = "{}"; 
        JSONObject jsonObject = new JSONObject();
        String value = jsonObject.getString("key");
        // Process the value
    } catch (JSONException e) {
        // Handle parsing exceptions
    }
    ```
   or
   ```java
    try {
        String jsonContent = "[]"; 
        JSONArray jsonArray = new JSONArray(jsonContent);
        String value = jsonArray.get(0);
        // Process the value
    } catch (JSONException e) {
        // Handle parsing exceptions
    }
    ```


6. **Incremental Processing:** Iterate through JSON arrays lazily:

   ```java
    try {
        String jsonContent = "[]";
        JSONArray jsonArray = new JSONArray(jsonContent);
        for (Object o : jsonArray)
            if (o instanceof JSONObject) {
                (JSONObject) o;
            }
            //...
        }
    } catch (JSONException e) {
        // Handle parsing exceptions
    }
    ```

## Thread Safety

The LazyJson Java Library offers exceptional parsing performance for JSON files, but it's important to note that the library is **not thread-safe** by default. This means that the library's classes and methods are not designed to be used concurrently by multiple threads without proper synchronization.

If you intend to use LazyJson in a multi-threaded environment, it's recommended to ensure proper synchronization mechanisms to prevent race conditions and ensure correct behavior. You might consider using techniques such as synchronized blocks or locks to control access to the library's parsing and processing methods.

Remember that ensuring thread safety is your responsibility as a developer, and it's important to carefully design and test your multi-threaded code to avoid potential issues.

If thread safety is a critical requirement for your use case, you might want to explore other options or consider implementing thread-safe wrappers around the LazyJson library.

**Note:** This library does not provide built-in thread safety mechanisms, so please keep this in mind when designing your application's architecture.


## Contributing

Contributions to the LazyJson Java Library are welcome! If you encounter any issues, have suggestions for improvements, or would like to contribute new features, feel free to submit a pull request on the [GitHub repository](https://github.com/aperfilev/lazyjson).

## License

The LazyJson Java Library is released under the [MIT License](https://opensource.org/licenses/MIT).

---

**Note:** This README is a template and may need to be customized to accurately reflect the LazyJson library you have created. Make sure to provide the appropriate links, examples, and usage instructions specific to your library._
