args<-commandArgs(TRUE);
train = read.csv(paste(args[1], args[2], sep = ""), header = TRUE)
train=train[complete.cases(train),]
rmse=function(x,y,k=0){
      return( sqrt(sum((x-y)^2)/(length(x)-k)))
}
require(MASS)
train$months <- as.factor(train$month)
train$date=NULL
model<-glm(impressions ~. , train, family=poisson)
model=stepAIC(model,trace=F)
summary(model)
test = read.csv(paste(args[1], args[3], sep = ""), header = TRUE)
test$months <- as.factor(test$month)
test$date=NULL

P = predict(model, newdata = test, type = "response")
imp = round(P)
imp

write.csv(imp, file = paste(args[1], args[4], sep = ""), fileEncoding = "UTF-8")