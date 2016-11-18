#!groovy
timestamps {
    node {
        catchError {
            checkout scm
            // Using mvn install to publish the library works as long as we have only one build node
            docker.image('maven:3.3.3-jdk-8').withRun('-it -v /var/lib/m2:/root/.m2', 'bash') {c ->
                sh """
                docker cp . ${c.id}:/root/netphony-network-protocols
                docker exec ${c.id} mvn -f /root/netphony-network-protocols install
                """
            }
        }
        step([$class: 'Mailer', recipients: '5gex-devel@tmit.bme.hu'])
    }
}
