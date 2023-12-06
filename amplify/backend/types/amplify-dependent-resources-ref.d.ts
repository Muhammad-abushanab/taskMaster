export type AmplifyDependentResourcesAttributes = {
  "analytics": {
    "taskmaster": {
      "Id": "string",
      "Region": "string",
      "appName": "string"
    }
  },
  "api": {
    "taskmaster": {
      "GraphQLAPIEndpointOutput": "string",
      "GraphQLAPIIdOutput": "string",
      "GraphQLAPIKeyOutput": "string"
    }
  },
  "auth": {
    "taskmasteref73980d": {
      "AppClientID": "string",
      "AppClientIDWeb": "string",
      "IdentityPoolId": "string",
      "IdentityPoolName": "string",
      "UserPoolArn": "string",
      "UserPoolId": "string",
      "UserPoolName": "string"
    }
  },
  "predictions": {
    "translateTextTaskMaster": {
      "region": "string",
      "sourceLang": "string",
      "targetLang": "string"
    }
  },
  "storage": {
    "taskMaster": {
      "BucketName": "string",
      "Region": "string"
    }
  }
}