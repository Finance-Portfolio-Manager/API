pipeline {
    agent any

    stages {
        stage('Compile') {
            steps {
                sh 'mvn clean package -Dmaven.test.skip=true'
            }
        }

        stage('Build') {
            steps {
                sh 'docker build -t dev/finportman-api .'
            }
        }

        stage('Deploy') {
            steps {
                build 'ApexStocks-Compose'
            }
        }
    }
}
