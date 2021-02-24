# oracle-jdbc-tester

A simple command line application to test JDBC connection to Oracle Database.

## How to run

Clone this repository and then run:

```
mvn clean package
```

Execute the JAR file:

```sh
java -jar jdbc-tester-1.1-jar-with-dependencies.jar
```

## How it works

The application connects to the Oracle database and executes the provided query or a default SQL query: `SELECT sysdate FROM dual` and prints the output. 

If it cannot connect for whatever reason, it will fail by logging an error message.

Original source: https://github.com/aimtiaz11/jdbc-tester

## License

(The MIT License)

THE SOFTWARE IS PROVIDED 'AS IS', WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
