def buildApp() {
    echo "Building jar..."
    sh 'mvn package'
}

def testApp() {
    sh 'mvn test'
}

def buildImage() {
    echo "Building docker image..." 
    withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]) {
        sh "docker build -t v8engine/java-maven-app:1.4.0 ."
        sh "echo ${PASSWORD} | docker login -u ${USERNAME} --password-stdin"
        sh "docker push v8engine/java-maven-app:1.4.0"
    }
}

def deploy() {
    echo "Deploying..."
}
return this