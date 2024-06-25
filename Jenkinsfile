pipeline {
    environment {
       registry = "9766945760/e-commerce-app"
       registryCredential = 'dockerhub-credentials'
       dockerImage = ''
    }
    agent any
    tools {
        jdk 'Jdk17'
        maven 'maven-3.8.6'
    }
    stages {
        stage('Git Checkout') {
            steps {
                git branch: 'main', changelog: false, poll: false, url: 'https://github.com/Angad-Raut/e-commerce-app.git'
            }
        }
        stage('Code Compile') {
            steps {
                bat 'mvn clean compile'
            }
        }
        stage('Unit Tests') {
            steps {
                bat 'mvn test'
            }
        }
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonar-server') {
                   bat 'mvn clean package sonar:sonar'
                }
            }
        }
        stage('OWASP SCAN') {
            steps {
                dependencyCheck additionalArguments: ' --scan ./ ', odcInstallation: 'DP-Check'
                dependencyCheckPublisher pattern: '**/dependency-check-report.xml'
            }
        }
        stage('Build Artifact') {
            steps {
                bat 'mvn clean package'
            }
        }
        stage('Docker Build') {
            steps{
                bat 'docker build -t 9766945760/e-commerce-app1 .'
            }
        }
        stage('Docker Push') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', passwordVariable: 'dockerhub_password', usernameVariable: 'dockerhub-username')]) {
                       bat "echo ${dockerhub_password} | docker login --username ${dockerhub-username} --password-stdin https://registry.docker.io"
                       bat 'docker tag e-commerce-app1 9766945760/e-commerce-app:latest'
                       bat 'docker push 9766945760/e-commerce-app:latest'
                    }
                }
            }
        }
        stage('Deploy To K8s') {
             steps {
                  script{
                      kubernetesDeploy (configs: 'deployment.yml',kubeconfigId: 'k8sconfig')
                  }
             }
        }
        stage('Remove Image') {
            steps{
                 bat 'docker rmi 9766945760/ecommerce-app54:$BUILD_NUMBER'
            }
        }
    }
}
