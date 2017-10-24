## Adding OAuth2 to Mobile Android and iOS Clients Using the AppAuth SDK

[OAuth2](https://oauth.net/2/), often combined with
[OpenID-Connect](http://openid.net/connect/), is a popular authorization
framework that enables applications to protect resources from unauthorized
access. It delegates user authentication to an authorization service, which then
authorizes third-party applications to access the protected resources on the
user’s behalf. OAuth 2 provides authorization flows for both web and mobile
applications. 

### AppAuth

[AppAuth](https://appauth.io/) is an open source SDK for native Android and iOS
apps which implements best-practice [OAuth2](https://tools.ietf.org/html/rfc6749) 
and [OpenID-Connect](http://openid.net/specs/openid-connect-core-1_0.html) (OIDC) 
specifications in a platform friendly manner.

The SDK follows [OAuth 2.0 for Native Apps](https://tools.ietf.org/html/draft-ietf-oauth-native-apps) best practices,
including the [PKCE](https://tools.ietf.org/html/rfc7636) extension and custom
tab browsers. The library provides hooks to further extend the protocol beyond
the basic flow.

As an open source project, [AppAuth](https://appauth.io/) has GitHub
repositories for [Android](https://github.com/openid/AppAuth-Android) and
[iOS](https://github.com/openid/AppAuth-iOS) which include good documentation, a
demo app, and integration with multiple authorization services.

### Getting Started

A sample app, implemented in Android, provides a concrete example using AppAuth
to authorize access to private resources. The Books App uses the Google Books
API and *Google* Sign-In services to search for books (protected by API key) and
show a signed-in user’s favorite book selections (protected by OAuth2). 
The app was developed on Android to
further explore AppAuth SDK usage with a common application architecture and
support libraries.

To follow along, start by cloning the Books demo project on GitHub available at
[github.com/approov](https://github.com/approov). It requires some
configuration, so it will not run out of the box. At a minimum, you will need to
provide a keystore, Google API key, and Google OAuth2 credentials.

See the article [Adding OAuth2 to Mobile Android and iOS Clients Using the AppAuth SDK](https://medium.com/@skiph/adding-oauth2-to-mobile-android-and-ios-clients-using-the-appauth-sdk-f8562f90ecff) 
for instructions on getting the app running.
