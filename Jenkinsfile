#!/usr/bin/env groovy

library identifier: 'jenkins-shared-library@main', retriever: modernSCM(
    [$class: 'GitSCMSource',
     remote: 'https://github.com/re-V8Engine/jenkins-shared-library.git',
     credentials: 'github-credentials'
    ]
)
def gv
def dockerRepo = 'v8engine'
def dockerImageName = 'java-maven-app'
def dockerTag
def version

pipeline {
    agent any
    tools {
        maven "maven-3.8"
    }

    stages {
        stage('init') {
            steps {
                script {
                    echo "Testing build trigger"
                    gv = load "script.groovy"
                }
            }
        }
        stage('increment version') {
            steps {
                script {
                    echo "Incrementing version..."
                    sh "mvn build-helper:parse-version versions:set \
                    -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} versions:commit"
                    def matcher = readFile('pom.xml') =~ '<version>(.*)</version>'
                    version = matcher[0][1]
                    dockerTag = "$version-$BUILD_NUMBER"
                }
            }
        }
        stage('test') {
            steps {
                echo "Executing pipeline for branch ${BRANCH_NAME}"
                echo "Testing application..."
                script {
                    gv.testApp()
                }
            }
        }
        stage('build jar') {
            steps {
                script {
                    buildApp()
                }
            }
        }
        stage('build image') {
            steps {
                script {
                    dockerBuild "$dockerRepo/$dockerImageName:$dockerTag"
                }
            }
        }
        stage('push image') {
            steps {
                script {
                    dockerLogin 'dockerhub-credentials'
                    dockerPush "$dockerRepo/$dockerImageName:$dockerTag"
                }
            }
        }
        stage('deploy') {
            steps {
                script {
                    echo "Deploying..."
                }
            }
        }
        stage('commit version update') {
            steps {
                script {
                    echo 'Testing jenkins push ignore....'
                    gitSetRemote('github-token', 'github.com/re-V8Engine/java-maven-app.git')
                    gitAddAll()
                    gitCommit "CI: version bump $version"
                    gitPush 'jenkins-jobs'
                }
            }
        }
    }
}
