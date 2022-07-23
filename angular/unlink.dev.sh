pushd `dirname "${BASH_SOURCE[0]}"` > /dev/null
for d in *; do
	dep="$d/target/dependencies/angular-dependencies/"
	if [ -d $dep ]; then
		rm -rf $dep
	fi

	dist="$d/dist"
	if [ -d $dist ]; then
		rm -rf $dist
	fi

	for f in *; do
		if [ -d $d ] && [ -d $f ]; then
			node="$d/node_modules/$f"
			if [ -L $node ]; then
				rm $node
			fi
		fi
	done
done
popd > /dev/null
