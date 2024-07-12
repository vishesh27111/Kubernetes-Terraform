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

resource "google_container_cluster" "primary" {
  name     = "my-cluster"
  location = "us-central1"

  remove_default_node_pool = true
  initial_node_count = 1

  # master_auth {
  #   username = ""
  #   password = ""

  #   client_certificate_config {
  #     issue_client_certificate = false
  #   }
  # }
}

resource "google_container_node_pool" "primary_nodes" {
  name       = "my-node-pool"
  location   = "us-central1"
  cluster    = google_container_cluster.primary.name
  node_count = 1

  node_config {
    # preemptible  = false
    machine_type = "e2-medium"

    # oauth_scopes = [
    #   "https://www.googleapis.com/auth/cloud-platform"
    # ]
  }
}
