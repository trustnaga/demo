# Nuxt 3 Minimal Starter

Look at the [Nuxt 3 documentation](https://nuxt.com/docs/getting-started/introduction) to learn more.

## Setup

Make sure to install the dependencies:

```bash
# yarn
yarn install

# npm
npm install

# pnpm
pnpm install
```

## Development Server

Start the development server on http://localhost:3000

```bash
npm run dev
```

## Production

Build the application for production:

```bash
npm run build
```

Locally preview production build:

```bash
npm run preview
```

Check out the [deployment documentation](https://nuxt.com/docs/getting-started/deployment) for more information.

Adding MSAL.js application

using yarn or Npm to install the msal.js library

1) Install the @azure/msal-browser package:
```bash
npm install @azure/msal-browser
```

2) Create a new file named ```auth.js``` in the ```plugins``` directory of your Nuxt 3 project:

```bash
    import { PublicClientApplication } from "@azure/msal-browser";

export default defineNuxtPlugin((nuxtApp) => {
    const msalConfig = {
        auth: {
            clientId: '<YOUR_APPLICAION_CLIENT_ID>',
            authority: 'https://login.microsoftonline.com/<YOU_APPLICAION_TENANT_ID>',
            redirectUri: '/', // Must be registered as a SPA redirectURI on your app registration
            postLogoutRedirectUri: 'http://localhost:3000/' // Must be registered as a SPA redirectURI on your app registrationregistration
        },
        cache: {
          cacheLocation: 'localStorage', // This configures where your cache will be stored
          storeAuthStateInCookie: false, // Set this to "true" if you are having issues on IE11 or Edge
        },
      }
    const msalInstance = new PublicClientApplication(msalConfig);
    return {
        provide: {
            msalInstance : msalInstance
        }
    }

})
```
Nuxt adds the plugin ```auth.js``` automatically to application. ``` msalInstance``` can be used in the application using

```bash
const { $msalInstance } = useNuxtApp();
```

For example in ```LoginButton.vue```.  Button will do login using popup
