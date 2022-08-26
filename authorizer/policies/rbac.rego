package app.rbac

import future.keywords.contains
import future.keywords.if
import future.keywords.in

default allow := false

allow if {
	some grant in user_is_granted

	input.action == grant.action
	input.type == grant.type
}

user_is_granted contains grant if {
	some role in data.user_roles[input.user]
	some grant in data.role_grants[role]
}