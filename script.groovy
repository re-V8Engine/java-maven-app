def testApp() {
    sh 'mvn test'
}

def deploy() {
    echo "Deploying..."
}
return this