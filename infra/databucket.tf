# Jim; this just fails ... commented it out ! We need to figure this out later, starting new task instead...
terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "4.40.0"
  }
}
}

resource "aws_s3_bucket" "analyticsbucket" {
  bucket = "analytics-${var.candidate_id}"
}