def buildApp() {
    echo "Building application..."
    echo "Building version ${APP_VERSION}..."
}

def testApp() {
    echo 'Testing application...'
}

def deployApp() {
    echo 'Deploying application...'
    echo "Deploying version ${params.VERSION}..."
}
return this