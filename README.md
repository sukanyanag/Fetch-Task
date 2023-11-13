# Fetch-Task

## Overview
Fetch Task App is an Android application designed to retrieve and display a list of items from a remote JSON endpoint (https://fetch-hiring.s3.amazonaws.com/hiring.json). The application groups these items by "listId," sorts them by "listId" and then by "name," and filters out any items where the "name" is blank or null. The final result is presented to the user in an easy-to-read list format.

## Features
- Fetches and processes data from a remote JSON endpoint.
- Groups and sorts items by 'listId' and 'name'.
- Filters out items with blank or null 'name' fields.
- Displays the processed list in a user-friendly RecyclerView.
- Implements Material Design for an aesthetically pleasing UI.

## Installation
To run this project, clone the repo, and import it into Android Studio.

```bash
git clone https://github.com/sukanyanag/Fetch-Task.git
```
## Dependencies

- Volley: For efficient network operations and data fetching.
- Android Material Components: For modern, material-styled UI elements.

## Screenshot

![Demo](https://github.com/sukanyanag/Fetch-Task/assets/60750067/cc95125e-73b9-43f6-a928-f7d65e78cfa0)



