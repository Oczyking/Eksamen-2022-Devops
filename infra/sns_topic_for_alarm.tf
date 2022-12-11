resource "aws_sns_topic" "alarms" {
  name = "alarm-topic-${var.candidate_id}"
}

resource "aws_sns_topic_subscription" "user_updates_sqs_target" {
  topic_arn = aws_sns_topic.alarms.arn
  protocol  = "email"
  endpoint  = var.candidate_email
}

resource "aws_cloudwatch_metric_alarm" "zerosum" {
  alarm_name                = "Carts-amount"
  namespace                 = "1042"
  metric_name               = "carts.value"

  comparison_operator       = "GreaterThanThreshold"
  threshold                 = "0"
  evaluation_periods        = "3"
  period                    = "300"

  statistic                 = "Maximum"

  alarm_description         = "This alarm goes off as soon as the total amount of carts exceeds more than 5 "
  insufficient_data_actions = []
  alarm_actions       = [aws_sns_topic.alarms.arn]
}