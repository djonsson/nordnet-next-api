# Nordnet Java Client

## What is it? 
The Swedish bank [Nordnet](https://www.nordnet.se) provides a programming interface to their systems that lets you integrate your own code with Nordnet. They call it [Nordnet External API](https://api.test.nordnet.se/api-docs/index.html) or nExt. This java client provide you with capabilities to interact with nExt through the REST api and over a SSL socket feed.

Using this client you will be able to build your own trading bot by acting on changes in the market and placing orders. 

## Documentation
- [Nordnet next2 client javadoc](http://example.com)
- [Nordnet External API](https://api.test.nordnet.se/api-docs/index.html)
- [Nordnet Feed](https://api.test.nordnet.se/next/2/api-docs/docs/feeds)

## Installation
1. [Register a test account on Nordnet](https://api.test.nordnet.se/account/register)
2. [Sign in to your test account](https://api.test.nordnet.se/login)
3. Once signed in, download the .pem file and place it in the root of the `main/java/resources` directory
4. Update `next-test-environment.properties` with your `username`, `password` and location of your `pemfile`
5. Run `mvn test` to run integration and unit tests 

## Examples

## Development

## Licensing

