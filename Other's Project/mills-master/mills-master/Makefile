.PHONY: push

push:
	-git add *
	@echo "Need comment for commit: (one line only)"
	-@read comment; \
	git commit -m "$$comment"
	git push origin

