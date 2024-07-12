terraform {
  required_providers {
    google = {
      source  = "hashicorp/google"
      version = "~> 3.5"
    }
  }
}

provider "google" {
  credentials = file("${path.module}/service-key.json")
  project = "app1-417719"
  region  = "us-central1"
}

# resource "google_container_cluster" "primary" {
#   name     = "my-cluster"
#   location = "us-central1"

#   remove_default_node_pool = true
#   initial_node_count       = 1

#   node_pool {
#     name       = "initial-node-pool"
#     node_count = 1
#     node_config {
#       machine_type = "e2-medium"
#     }
#   }
# }