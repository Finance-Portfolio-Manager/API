pipeline {
   agent any

    environment {
        PORT="8082"
        IMAGE_TAG="portfolio-manager"
        CONTAINER_NAME="portfolio-manager-app"
        DB_USER=credentials('db-username')
        DB_PASS=credentials('db-password')
    }

   stages {
      stage('checkout'){
          steps {
            script {
                properties([pipelineTriggers([githubPush()])])
            }
            git branch: 'main', url: 'https://gitlab.com/210614-java-react-enterprise/training-repositories/spring-boot-demo.git'

          }
      }
      stage('clean') {
         steps {
            sh 'mvn clean'
         }
      }
      stage('package') {
         steps {
            sh 'mvn package -Dmaven.test.skip=true'
         }
      }
      stage('remove previous image if exists') {
            steps {
                sh 'docker rmi -f ${IMAGE_TAG} || true'
            }
        }

       stage('create image') {
            steps {
                sh 'docker build -t ${IMAGE_TAG} -f Dockerfile .'
            }
        }
        stage('remove previous container if exists') {
            steps {
                sh 'docker stop ${CONTAINER_NAME} || true'
            }
        }
        stage('create container') {
            steps {
                sh 'docker run -e DB_USER=${DB_USER} -e DB_PASS=${DB_PASS} -d --rm -p ${PORT}:${PORT} --name ${CONTAINER_NAME} ${IMAGE_TAG} '
            }
        }
    }
}
