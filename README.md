# Choas-monkey

### What is Chaos Eng -introductions including how to start your first chaos experiment:
https://www.gremlin.com/community/tutorials/chaos-engineering-the-history-principles-and-practice/

### Good summary of people, tools, companies doing chaos experiments: 
https://coggle.it/diagram/WiKceGDAwgABrmyv/t/chaos-engineeringcompanies%2C-people%2C-tools-practices/0a2d4968c94723e48e1256e67df51d0f4217027143924b23517832f53c536e62

##Tools:

### Spinnaker: https://www.spinnaker.io/. Netflix Chaos Monkey does not support deployments that are managed by anything other than Spinnaker. That makes it pretty hard to use Chaos Monkey from Netflix.

### ChaosMonkey for SpringBoot: https://docs.chaostoolkit.org/drivers/cloudfoundry/. Very easy to follow instructions. Easy to turn on/off using Spring profile.

### Chaos Toolkit - https://docs.chaostoolkit.org/drivers/cloudfoundry/. This tool is particularly helpful to my situation since my applications are deployed in Cloud Foundry and this tool has a CloudFoundry extension. Pretty elaborate, but easy to follow instructions. My preferred tool so far.

### Chaos Lemur - https://content.pivotal.io/blog/chaos-lemur-testing-high-availability-on-pivotal-cloud-foundry. This tool has promise but network admin won't share AWS credentials for me to muck with Pivotal cells.

